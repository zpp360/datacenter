package com.shuheng.datacenter.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiConstants {

    public static final Map<String,String> ERROR_MAP;

    public static final String SYS_PLAT_SUCCESS = "请求成功";

    public static final String CODE_200 = "200";
    /**
     * 参数错误
     */
    public static final String CODE_201 = "201";
    public static final String CODE_202 = "202";



    static{
        Map<String,String> map = new HashMap<String, String>();
        map.put("200", "success");
        map.put("201", "参数错误");
        map.put("202", "系统异常");


        ERROR_MAP = Collections.unmodifiableMap(map);

    }
}
