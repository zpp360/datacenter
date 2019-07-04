package com.shuheng.datacenter.controller;

import com.alibaba.fastjson.JSON;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.entity.Unit;
import com.shuheng.datacenter.entity.User;
import com.shuheng.datacenter.server.AppUserService;
import com.shuheng.datacenter.server.SyncDataversionService;
import com.shuheng.datacenter.server.UnitService;
import com.shuheng.datacenter.utils.Const;
import com.shuheng.datacenter.utils.DESUtil;
import com.shuheng.datacenter.utils.DateUtil;
import com.shuheng.datacenter.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@PropertySource({"classpath:/application.properties"})
@Component
@RestController
public class ScheduledTasks {

    private static Logger log = Logger.getLogger(ScheduledTasks.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${datacenter.url}")
    private String url;

    @Value("${system_name}")
    private String systemName;

    @Value("${method_unit}")
    private String methodUnit;

    @Value("${method_user}")
    private String methodUser;

    @Autowired
    private RestTemplate restTemplate;
    @Resource(name = "unitService")
    private UnitService unitService;

    @Resource(name = "syncDataversionService")
    private SyncDataversionService syncDataversionService;

    @Resource(name = "appUserService")
    private AppUserService appUserService;

    /**
     * 从数据中心增量获取单位
     * 增量获取
     * 每天半夜12点30分执行一次（注意日期域为0不是24）
     */
    @Scheduled(cron = "0 40 0 * * ?")
    @GetMapping(value = "/syncUnit")
    public void syncUnit() {
        log.error("单位同步任务开始："+DateUtil.fomatDate(sdf.format(new Date())));
        Map<String,String> params = new HashMap<String, String>();
        String timestamp = "";
        try {
            timestamp = syncDataversionService.findDataVersionByNameAndMethod(systemName,methodUnit);
            if(StringUtils.isBlank(timestamp)){
                timestamp = "194505060000";
            }
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
                return;
            }
            log.error(DateUtil.fomatDate(sdf.format(new Date())+"单位操作开始"));
            List<Unit> insertList = new ArrayList<>();
            List<Unit> updateList = new ArrayList<>();
            List<Unit> delList = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                Unit unit = list.get(i);
                if(unit==null || unit.getUnit_id()=="-1"){
                    continue;
                }
                if("1".equals(unit.getDel_flag())){
                    //del_flag不为0
                    delList.add(unit);
                    continue;
                }
                if(!"0".equals(unit.getDel_flag())){
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
                    if(Long.parseLong(unit.getUpdate_time()) > Long.parseLong(timestamp)){
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

            //删除del_flag为1的单位
            if(delList!=null && delList.size()>0){
                deleteUnit(delList);
            }

            //更新版本号
            if(StringUtils.isNotBlank(timestamp)){
                try {
                    syncDataversionService.updateDataVersion(systemName,methodUnit,timestamp);
                } catch (Exception e) {
                    log.error("单位同步更新版本号出错");
                    e.printStackTrace();
                }
            }

            log.error("单位同步任务结束："+DateUtil.fomatDate(sdf.format(new Date())));
        }
    }

    /**
     * 删除单位，应该先进行同步用户的任务，在进行同步单位的任务，要不会因为单位下存在用户导致单位删除失败
     * @param delList
     */
    private void deleteUnit(List<Unit> delList) {
        for (int i=0; i<delList.size(); i++){
            Unit unit = delList.get(i);
            long count = 0;
            try {
                count = unitService.countAppUserByUnitId(unit.getUnit_id());
            } catch (Exception e) {
                log.error("根据单位id查询APP用户数量出错");
                e.printStackTrace();
            }
            if(count>0){
                log.error("单位删除失败，因为单位下存在APP用户。单位id:"+unit.getUnit_id()+",单位名称:"+unit.getUnit_name_full());
                delList.remove(unit);
            }
        }
        try {
            if(delList.size()>0){
                unitService.batchDelete(delList);
            }
        } catch (Exception e) {
            log.error("批量删除单位执行出错。");
            e.printStackTrace();
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

    /**
     * 更新单位的unit_path
     * 每天半夜12:40分执行,确保在单位同步完成之后再执行
     */
    @Scheduled(cron = "0 50 0 * * ?")
    @GetMapping(value = "updateUnitPath")
    public void updateUnitPath(){
        log.error("单位上下级路径更新任务开始："+DateUtil.fomatDate(sdf.format(new Date())));
        List<PageData> updateList = new ArrayList<>(200);
        PageData param = new PageData();
        param.put("city","171");
        List<PageData> unitList = null;
        try {
            unitList = unitService.listByCity(param);
        } catch (Exception e) {
            log.error("单位上下级路径更新任务，获取青岛市所有单位出错");
            e.printStackTrace();
        }
        if(unitList!=null && unitList.size()>0){
            //挨个单位获取更新
            for (PageData unitPd : unitList){
                String unitId = unitPd.getString("UNIT_ID");
                String parentId = unitPd.getString("UNIT_P_ID");
                if(StringUtils.isNotBlank(parentId)){
                    String unitPath = unitId;
                    if(StringUtils.isNotBlank(parentId)){
                        try {
                            unitPath = getUnitPath(parentId,unitPath);
                            unitPd.put("unit_path",unitPath);
                            updateList.add(unitPd);
                        } catch (Exception e) {
                            log.error("单位上下级路径更新任务，获取单位路径出错");
                            e.printStackTrace();
                        }
                    }
                }
                if(updateList.size()>200){
                    try {
                        batchUpdateUnitPath(updateList);
                    } catch (Exception e) {
                        log.error("单位上下级路径更新任务，批量更新单位路径出错");
                        e.printStackTrace();
                    }
                }
            }

            if(updateList.size()>0) {
                try {
                    batchUpdateUnitPath(updateList);
                } catch (Exception e) {
                    log.error("单位上下级路径更新任务，批量更新单位路径出错");
                    e.printStackTrace();
                }
            }
        }
        log.error("单位上下级路径更新任务结束："+DateUtil.fomatDate(sdf.format(new Date())));
    }

    /**
     * 批量更新
     * @param list
     * @throws Exception
     */
    private void batchUpdateUnitPath(List<PageData> list) throws Exception {
        unitService.batchUpdateUnitPath(list);
        list.clear();
    }

    /**
     * 获取单位树路径，不包含自身，在sql保存的的时候添加自身
     * @param pId
     * @param unitPath
     * @return
     * @throws Exception
     */
    private String getUnitPath(String pId, String unitPath) throws Exception {
        PageData pd = unitService.findById(pId);
        if(pd!=null&&StringUtils.isNotBlank(pd.getString("UNIT_P_ID"))){
            unitPath = pId + "|" + unitPath;
            return getUnitPath(pd.getString("UNIT_P_ID"),unitPath);
        }
        return unitPath;
    }

    /**
     * 同步人员
     */
    @Scheduled(cron = "0 30 0 * * ?")
    @GetMapping(value = "syncUser")
    public void syncUser(){
        log.error("人员同步任务开始："+DateUtil.fomatDate(sdf.format(new Date())));
        Map<String,String> params = new HashMap<String, String>();
        String timestamp = "";
        try {
            timestamp = syncDataversionService.findDataVersionByNameAndMethod(systemName,methodUser);
            if(StringUtils.isBlank(timestamp)){
                timestamp = "194505060000";
            }
        } catch (Exception e) {
            log.error("单位同步获取版本号出错");
            e.printStackTrace();
        }
        params.put("updatetime",timestamp);
        params.put("orgType","3");
        params.put("flag","in");
        ResponseEntity resEntity = restTemplate.getForEntity(url,String.class,params);
        if(resEntity!=null && resEntity.getStatusCodeValue()== Const.SUCCESS_CODE) {
            //响应成功，获取数据
            String json = (String) resEntity.getBody();
            List<User> list = JSON.parseArray(json,User.class);
            if(list==null || list.size()<1){
                log.error(DateUtil.fomatDate(sdf.format(new Date())+"本次未获取到任何人员"));
                return;
            }
            log.error(DateUtil.fomatDate(sdf.format(new Date())+"人员操作开始"));
            List<User> insertList = new ArrayList<>();
            List<User> updateList = new ArrayList<>();
            List<User> delList = new ArrayList<>();
            for (int i=0; i < list.size(); i++){
                User user = list.get(i);

                if("1".equals(user.getDel_flag())){
                    //删除用户
                    delList.add(user);
                    continue;
                }

                if(!"0".equals(user.getDel_flag())){
                    log.info("人员错误，人员del_flag不为0，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                if(StringUtils.isBlank(user.getUser_id())){
                    log.info("人员错误，人员ID不能为空，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                if(StringUtils.isBlank(user.getUser_name())){
                    log.info("人员错误，人员姓名不能为空，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                if(StringUtils.isBlank(user.getUnit_id())){
                    log.info("人员错误，人员单位不能为空，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                if(StringUtils.isBlank(user.getUser_type())){
                    log.info("人员错误，人员类型不能为空，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                if(StringUtils.isBlank(user.getReport_flag())){
                    log.info("人员错误，人员居住地报到标识不能为空，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                    continue;
                }

                //设置phone_type
                if(StringUtils.isNotBlank(user.getMobilephone()) && ValidateUtils.Mobile(user.getMobilephone())){
                    user.setPhone_type("0");
                }else{
                    user.setPhone_type("1");
                }

                if(StringUtils.isNotBlank(user.getMobilephone())){
                    //手机号不为空，加密
                    try {
                        user.setMobilephone(DESUtil.encode(user.getMobilephone()));
                    } catch (Exception e) {
                        log.error("人员手机号加密错误，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                        e.printStackTrace();
                    }
                }

                if(StringUtils.isNotBlank(user.getCard_number())){
                    //身份证号码不为空，加密
                    try {
                        user.setCard_number(DESUtil.encode(user.getCard_number()));
                    } catch (Exception e) {
                        log.error("人员身份证号码加密错误，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                        e.printStackTrace();
                    }
                }

                //处理生日和入党时间，如果是6位的。补上01
                if(StringUtils.isNotBlank(user.getBirthday())){
                    if(user.getBirthday().length()==6){
                        user.setBirthday(user.getBirthday()+"01");
                    }
                    if(user.getBirthday().length()==8 && user.getBirthday().endsWith("00")){
                        user.setBirthday(user.getBirthday().substring(0,6)+"01");
                    }
                }
                if(StringUtils.isNotBlank(user.getRd_time())){
                    if(user.getRd_time().length()==6){
                        user.setRd_time(user.getRd_time()+"01");
                    }
                    if(user.getRd_time().length()==8 && user.getRd_time().endsWith("00")){
                        user.setRd_time(user.getRd_time().substring(0,6)+"01");
                    }
                }

                //返回值没有问题，迭代timestamp，并存储timestamp
                if (StringUtils.isNotBlank(user.getUpdate_time())){
                    if(Long.parseLong(user.getUpdate_time()) > Long.parseLong(timestamp)){
                        timestamp = user.getUpdate_time();
                    }
                }

                try {
                    PageData pd = appUserService.findById(user.getUser_id());
                    if(pd!=null){
                        updateList.add(user);
                    }else{
                        insertList.add(user);
                    }
                } catch (Exception e) {
                    log.error("查询人员信息出错");
                    e.printStackTrace();
                }

                if(insertList.size()>200){
                    try {
                        userBatchSave(insertList);
                    } catch (Exception e) {
                        log.error("批量保存用户出错");
                        e.printStackTrace();
                    }
                }
                if(updateList.size()>200){
                    try {
                        appUserService.batchUpdate(updateList);
                    } catch (Exception e) {
                        log.error("批量更新用户出错");
                        e.printStackTrace();
                    }
                }

            }
            if(delList!=null && delList.size()>0){
                try {
                    appUserService.batchDel(delList);
                } catch (Exception e) {
                    log.error("批量删除用户出错");
                    e.printStackTrace();
                }
            }

            if(insertList!=null && insertList.size()>0){
                try {
                    userBatchSave(insertList);
                } catch (Exception e) {
                    log.error("批量保存用户出错");
                    e.printStackTrace();
                }
            }

            if(updateList!=null && updateList.size()>0){
                try {
                    appUserService.batchUpdate(updateList);
                } catch (Exception e) {
                    log.error("批量更新用户出错");
                    e.printStackTrace();
                }
            }

            //更新版本号
            if(StringUtils.isNotBlank(timestamp)){
                try {
                    syncDataversionService.updateDataVersion(systemName,methodUser,timestamp);
                } catch (Exception e) {
                    log.error("单位同步更新版本号出错");
                    e.printStackTrace();
                }
            }
        }

        log.error("单位同步任务结束："+DateUtil.fomatDate(sdf.format(new Date())));
    }

    /**
     * 批量插入用户
     * @param list
     */
    private void userBatchSave(List<User> list) throws Exception {
        List<User> repetList = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            User user = list.get(i);
            //检查是否存在此手机号
            if(StringUtils.isNotBlank(user.getMobilephone())){
                try {
                    PageData pd = appUserService.findByPhone(user.getMobilephone());
                    if(pd!=null){
                        log.error("人员手机号出现重复，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                        repetList.add(user);
                        continue;
                    }
                    //继续判断列表中是否有重复手机号的，避免批量插入的时候插入重复的报错
                    for (int j = i+1; j< list.size(); j++ ){
                        if(user.getMobilephone().equals(list.get(j).getMobilephone())){
                            log.error("人员手机号出现重复，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                            repetList.add(user);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    log.error("根据手机号查询人员出错");
                    e.printStackTrace();
                }

            }
            //检查身份证号码是否存在
            if(StringUtils.isNotBlank(user.getCard_number())){
                try {
                    PageData pd = appUserService.findByCardNumber(user.getCard_number());
                    if(pd!=null){
                        log.error("人员身份证号出现重复，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                        repetList.add(user);
                        continue;
                    }
                    //继续判断列表中是否有重复手机号的，避免批量插入的时候插入重复的报错
                    for (int j = i+1; j< list.size(); j++ ){
                        if(user.getCard_number().equals(list.get(j).getCard_number())){
                            log.error("人员身份证号出现重复，用户id："+user.getUser_id() + ",用户姓名："+user.getUser_name());
                            repetList.add(user);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    log.error("根据身份证号查询人员出错");
                    e.printStackTrace();
                }
            }
        }
        list.removeAll(repetList);
        appUserService.batchSave(list);
        list.clear();
    }

    private void userBatchUpdate(List<User> list) throws Exception {
        appUserService.batchUpdate(list);
        list.clear();
    }

}



//        0 0 12 * * ? 每天12点触发
//        0 15 10 ? * * 每天10点15分触发
//        0 15 10 * * ? 每天10点15分触发
//        0 15 10 * * ? * 每天10点15分触发
//        0 15 10 * * ? 2005 2005年每天10点15分触发
//        0 * 14 * * ? 每天下午的 2点到2点59分每分触发
//        0 0/5 14 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发)
//        0 0/5 14,18 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发)
//        每天下午的 18点到18点59分(整点开始，每隔5分触发)
//        0 0-5 14 * * ? 每天下午的 2点到2点05分每分触发
//        0 10,44 14 ? 3 WED 3月分每周三下午的 2点10分和2点44分触发 （特殊情况，在一个时间设置里，执行两次或两次以上的情况）
//        0 59 2 ? * FRI 每周5凌晨2点59分触发
//        0 15 10 ? * MON-FRI 从周一到周五每天上午的10点15分触发
//        0 15 10 15 * ? 每月15号上午10点15分触发
//        0 15 10 L * ? 每月最后一天的10点15分触发
//        0 15 10 ? * 6L 每月最后一周的星期五的10点15分触发
//        0 15 10 ? * 6L 2002-2005 从2002年到2005年每月最后一周的星期五的10点15分触发
//        0 15 10 ? * 6#3 每月的第三周的星期五开始触发
//        0 0 12 1/5 * ? 每月的第一个中午开始每隔5天触发一次
//        0 11 11 11 11 ? 每年的11月11号 11点11分触发(光棍节)
//        0 51 15 26 4 ? 2016 2016年4月26日15点51分出发
//        0 30 0 * * ?  每天半夜12点30分执行一次（注意日期域为0不是24）
//        0 0 2 * * ?   每天凌晨2点
//        */5 * * * * ?  每隔5秒执行一次
//        0 */1 * * * ?  每隔1分钟执行一次


