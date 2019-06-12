package com.shuheng.datacenter.interceptor;

import com.alibaba.fastjson.JSON;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.utils.DateUtil;
import com.shuheng.datacenter.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonParamsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiData data = new ApiData();
        response.setCharacterEncoding("utf-8");
        String pageSize = request.getParameter("pageSize");
        String pageNumber = request.getParameter("pageNumber");
        String timestamp = request.getParameter("timestamp");
        boolean flag = true;
        //验证pageSize
        if(StringUtils.isNotBlank(pageSize)){
            if(!ValidateUtils.Z_index(pageSize)){
                data.setErrorCode(ApiConstants.CODE_206);
                flag = false;
            }
        }
        //验证pageNumber
        if(StringUtils.isNotBlank(pageNumber)){
            if(!ValidateUtils.Z_index(pageNumber)){
                data.setErrorCode(ApiConstants.CODE_207);
                flag = false;
            }
        }
        //验证timestamp
        if(StringUtils.isNotBlank(timestamp)){
            if(!DateUtil.isValidDate(timestamp)){
                //日期不合法
                data.setErrorCode(ApiConstants.CODE_205);
                flag = false;
            }
        }

        //pageSize或者pageNumber不是正整数，返回错误码
        if(!flag){
            response.getWriter().write(JSON.toJSONString(data));
        }
        return flag;
    }
}
