package com.shuheng.datacenter.interceptor;

import com.alibaba.fastjson.JSON;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("accessToken");
        //验证成功才返回true
        if(null != token){
            boolean result = JwtUtil.verify(token);
            if(result){
                return true;
            }
        }
        //验证失败给用户返回错误码
        ApiData data = new ApiData();
        data.setErrorCode(ApiConstants.CODE_204);
        response.getWriter().write(JSON.toJSONString(data));
        return false;
    }
}
