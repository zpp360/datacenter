package com.shuheng.datacenter.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiData implements Serializable {

    private static final long serialVersionUID = 8689409753312953532L;


    private String errorCode;
    private String errorMsg;
    private Map<String, Object> data = null;
    private List<Map<String,String>> datas = null;


    /**
     * 构造器： 请求成功
     */
    public ApiData() {
        this.errorCode = ApiConstants.SYS_PLAT_SUCCESS; // 设置成功
    }

    public ApiData(Map<String, Object> data) {
        this();
        this.data = data;
    }

    /**
     * 构造器 : 数据放在 data下
     * @param key 键
     * @param value 响应对应的对象值
     */
    public ApiData(String key, Object value) {
        this();
        this.put(key, value);
    }

    /**
     * @Description: 添加响应消息
     * @author: 作者
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        if(null == data) {
            data = new HashMap<String, Object>();
        }
        data.put(key, value);
    }

    /**
     * 添加相应信息
     * @Description:
     * @author: 作者
     * @param prams
     */
    public void put(Map<String, Object> prams) {
        if (prams != null && !prams.isEmpty()) {
            Object[] keys = prams.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                put(key, prams.get(key));
            }
        }
    }

    /**
     * @Description: 节点下增加指定值,只支持原先是map对象,或者新开启
     * @author: 作者
     * @param node
     * @param key
     * @param value
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void put(String node, String key, Object value) {
        Object obj = data.get(node);
        if(null == obj) {
            obj = new HashMap<>();
            data.put(node, obj);
        }

        if(!(obj instanceof Map)) {
            throw new RuntimeException("[不支持非Map节点增加键值对]");
        }

        Map map = (Map) obj;
        map.put(key, value);
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        String errorMsg = ApiConstants.ERROR_MAP.get(errorCode);
        if(StringUtils.isNotBlank(errorMsg)) {
            this.errorMsg = errorMsg;
        }
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<Map<String, String>> getDatas() {
        return datas;
    }

    public void setDatas(List<Map<String, String>> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "ApiData{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                ", datas=" + datas +
                '}';
    }
}
