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
    /**
     * 系统异常
     */
    public static final String CODE_202 = "202";
    /**
     * token获取验证失败
     */
    public static final String CODE_203 = "203";
    /**
     * token验证失败
     */
    public static final String CODE_204 = "204";
    /**
     * timestamp时间戳校验失败
     */
    public static final String CODE_205 = "205";
    /**
     * pageSize校验失败
     */
    public static final String CODE_206 = "206";
    /**
     * pageNumber校验失败
     */
    public static final String CODE_207 = "207";



    static{
        Map<String,String> map = new HashMap<String, String>();
        map.put("200", "success");
        map.put("201", "参数错误");
        map.put("202", "系统异常");
        map.put("203", "token获取验证失败");
        map.put("204", "token验证失败");
        map.put("205", "timestamp时间戳校验失败");
        map.put("206", "pageSize校验失败");
        map.put("207", "pageNumber校验失败");


        ERROR_MAP = Collections.unmodifiableMap(map);

    }
}
