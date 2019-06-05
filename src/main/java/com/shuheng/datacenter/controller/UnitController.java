package com.shuheng.datacenter.controller;

import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.UnitService;
import com.shuheng.datacenter.utils.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource({"classpath:/application.properties"})
@RestController
public class UnitController {

    private static Logger log = Logger.getLogger(UnitController.class);

    @Value("${datacenter.list-unit}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;
    @Resource(name = "unitService")
    private UnitService unitService;

    /**
     * 从数据中心增量获取单位
     * 增量获取
     */
    @GetMapping("/syncUnit")
    public Object syncUnit(){
        Map<String,String> params = new HashMap<String, String>();
        boolean flag = true;
        ResponseEntity resEntity = null;
        while (flag){
            resEntity = restTemplate.getForEntity(url,ApiData.class,params);
            if(resEntity!=null && resEntity.getStatusCodeValue()== Const.SUCCESS_CODE){
                //响应成功，获取数据
                ApiData apiData = (ApiData) resEntity.getBody();
                System.out.println(apiData);
            }
            flag = false;
        }
        log.error("sdfsdfsdfssdfsdfsd");
        return resEntity.getBody();
    }

}
