package com.shuheng.datacenter.controller;

import com.alibaba.fastjson.JSON;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.entity.Unit;
import com.shuheng.datacenter.server.SyncDataversionService;
import com.shuheng.datacenter.server.UnitService;
import com.shuheng.datacenter.utils.Const;
import com.shuheng.datacenter.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@PropertySource({"classpath:/application.properties"})
@Component
public class ScheduledTasks {

    private static Logger log = Logger.getLogger(ScheduledTasks.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${datacenter.url}")
    private String url;

    @Value("${system_name}")
    private String systemName;

    @Value("${method_unit}")
    private String methodName;

    @Autowired
    private RestTemplate restTemplate;
    @Resource(name = "unitService")
    private UnitService unitService;

    @Resource(name = "syncDataversionService")
    private SyncDataversionService syncDataversionService;

    /**
     * 从数据中心增量获取单位
     * 增量获取
     * 每天半夜12点30分执行一次（注意日期域为0不是24）
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void syncUnit() {
        log.error("单位同步任务开始："+DateUtil.fomatDate(sdf.format(new Date())));
        Map<String,String> params = new HashMap<String, String>();
        String timestamp = "";
        try {
            timestamp = syncDataversionService.findDataVersionByNameAndMethod(systemName,methodName);
        } catch (Exception e) {
            log.error("单位同步获取版本号出错");
            e.printStackTrace();
        }
        params.put("updatetime",timestamp);
        params.put("orgType","1");
        params.put("flag","in");
        ResponseEntity resEntity = restTemplate.getForEntity(url,String.class,params);
        if(resEntity!=null && resEntity.getStatusCodeValue()== Const.SUCCESS_CODE){
            //响应成功，获取数据
            String json = (String) resEntity.getBody();
            //将json数据使用FastJson转成对象
            List<Unit> list = JSON.parseArray(json,Unit.class);
            if(list==null || list.size()<1){
                log.error(DateUtil.fomatDate(sdf.format(new Date())+"本次未获取到任何单位"));
            }
            log.error(DateUtil.fomatDate(sdf.format(new Date())+"单位操作开始"));
            List<Unit> insertList = new ArrayList<Unit>();
            List<Unit> updateList = new ArrayList<Unit>();
            for (int i=0;i<list.size();i++){
                Unit unit = list.get(i);
                if(unit==null || unit.getUnit_id()=="-1"){
                    continue;
                }
                if(!"0".equals(unit.getDel_flag())){
                    System.out.println(unit.getDel_flag());
                    //del_flag不为0
                    log.error("单位错误，del_flag不为0，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                    continue;
                }
                if(StringUtils.isBlank(unit.getUnit_type())){
                    //单位类型为空，直接过滤掉
                    log.error("单位错误，unit_type为null，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                    continue;
                }
                if(StringUtils.isBlank(unit.getUnit_id()) || StringUtils.isBlank(unit.getUnit_p_id()) || StringUtils.isBlank(unit.getUnit_name_full())){
                    //del_flag不为0
                    log.error("单位错误，缺少关键项，单位id，上级id或者名称，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                    continue;
                }
                if(unit.getUnit_type()=="1" || unit.getUnit_type()=="2" || unit.getUnit_type()=="3"){
                    //中直，省直，市直
                    if(StringUtils.isBlank(unit.getProvince()) || StringUtils.isBlank(unit.getCity())){
                        //如果省和市为空，则记录日志
                        log.error("单位错误，中直省直市直单位，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                        continue;
                    }
                }
                if(unit.getUnit_type()=="4"){
                    //街道
                    if(StringUtils.isBlank(unit.getProvince()) || StringUtils.isBlank(unit.getCity()) || StringUtils.isBlank(unit.getStreet())){
                        //如果省和市为空，则记录日志
                        log.error("单位错误，街道单位，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                        continue;
                    }
                }
                if(unit.getUnit_type()=="5"){
                    //街道
                    if(StringUtils.isBlank(unit.getProvince()) || StringUtils.isBlank(unit.getCity()) || StringUtils.isBlank(unit.getStreet()) || StringUtils.isBlank(unit.getCommunity())){
                        //如果省和市为空，则记录日志
                        log.error("单位错误，社区单位，单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                        continue;
                    }
                }
                //返回值没有问题，迭代timestamp，并存储timestamp
                if (StringUtils.isNotBlank(unit.getUpdate_time())){
                    if(Integer.parseInt(unit.getUpdate_time()) > Integer.parseInt(timestamp)){
                        timestamp = unit.getUpdate_time();
                    }
                }


                PageData pd = null;
                try {
                    pd = unitService.findById(unit.getUnit_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(pd!=null){
                    //存在，更新
                    updateList.add(unit);
                }else{
                    insertList.add(unit);
                }

                if(insertList.size()>200){
                    //当数量大于200时执行批量插入
                    try {
                        batchInsert(insertList);
                    } catch (Exception e) {
                        log.error("批量插入执行出错");
                        e.printStackTrace();
                    }
                }

                if(updateList.size()>200){
                    //批量更新
                    try {
                        batchUpdate(updateList);
                    } catch (Exception e) {
                        log.error("批量更新出现错误");
                        e.printStackTrace();
                    }
                }
            }
            //再执行一遍更新插入操作，因为最后一次循环有不到200条的情况
            if(insertList.size()>0){
                try {
                    batchInsert(insertList);
                } catch (Exception e) {
                    log.error("批量插入出现错误");
                    e.printStackTrace();
                }
            }

            //批量更新
            if(updateList.size()>0){
                try {
                    batchUpdate(updateList);
                } catch (Exception e) {
                    log.error("批量更新出现错误");
                    e.printStackTrace();
                }
            }

            //更新版本号
            if(StringUtils.isNotBlank(timestamp)){
                try {
                    syncDataversionService.updateDataVersion(systemName,methodName,timestamp);
                } catch (Exception e) {
                    log.error("单位同步更新版本号出错");
                    e.printStackTrace();
                }
            }

            log.error("单位同步任务结束："+DateUtil.fomatDate(sdf.format(new Date())));
        }
    }

    /**
     * 批量插入
     * @param list
     */
    private void batchInsert(List<Unit> list) throws Exception {
        unitService.batchSave(list);
        list.clear();
    }

    /**
     * 批量更新
     * @param list
     * @throws Exception
     */
    private void batchUpdate(List<Unit> list) throws Exception {
        unitService.batchUpdate(list);
        list.clear();
    }

}
