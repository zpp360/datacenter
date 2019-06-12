package com.shuheng.datacenter.api;

import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.server.APPService;
import com.shuheng.datacenter.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class TokenApi {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "appService")
    private APPService appService;
    /**
     * 验证APPId是否正确
     * @param appId     appid
     * @param timestamp  时间戳
     * @param random    随机字符串
     * @param signature  由调用端使用appScreat，timestamp，random自然排序后进行sha1加密获得
     * @return
     */
    @PostMapping(value = "token")
    public ApiData validataAppKey(String appId, String timestamp, String random, String signature){
        ApiData data = new ApiData();
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(random) || StringUtils.isBlank(signature)){
            data.setErrorCode(ApiConstants.CODE_201);
        }
        //根据appId获取appScreat

        try {
            String appScreat = appService.getAppScreateByAppId(appId);
            String checkSignature = createSignature(appScreat,timestamp,random);
            if(checkSignature.equals(signature)){
                //验证成功,生成token
                String accessToken = JwtUtil.sign(appId);
                data.put("accessToken",accessToken);
                data.put("expiresIn",JwtUtil.EXPIRE_TIME);
                data.setErrorCode(ApiConstants.CODE_200);
            }else{
                //验证失败
                data.setErrorCode(ApiConstants.CODE_203);
            }

        } catch (Exception e) {
            data.setErrorCode(ApiConstants.CODE_202);
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 创建signature
     * @param appScreat
     * @param timestamp
     * @param random
     * @return
     */
    private String createSignature(String appScreat,String timestamp,String random){
        List<String> list = new ArrayList<String>();
        String checksignature = "";
        if(StringUtils.isNotBlank(appScreat) && StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(random)){
            list.add(appScreat);
            list.add(timestamp);
            list.add(random);
            Collections.sort(list);
            checksignature=getSha1(list.get(0)+list.get(1)+list.get(2));
        }
        return checksignature;
    }

    /**
     * sha1签名
     * @param str
     * @return
     */
    private String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }


}


