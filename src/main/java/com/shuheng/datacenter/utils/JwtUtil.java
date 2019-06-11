package com.shuheng.datacenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    /**
     * 过期时间15分钟
     */
    public static final long EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "372301199201082911";

    /**
     * 生成签名，15min后过期
     * @param appId
     * @return 加密后的token
     */
    public static String sign(String appId){
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<String,Object>(2);
            header.put("typ","JWT");
            header.put("alg","HS256");
            //附带appId和appName信息，生成签名
            return JWT.create()//
                    .withHeader(header)//
                    .withClaim("appId",appId)//
                    .withExpiresAt(date)//
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
