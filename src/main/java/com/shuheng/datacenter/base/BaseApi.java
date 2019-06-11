package com.shuheng.datacenter.base;

import com.shuheng.datacenter.entity.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


@PropertySource({"classpath:/application.properties"})
public class BaseApi {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Value("${global.pageSize}")
    private String pageSize;
    /**
     * 获取分页信息
     * @param pageNumber 页码
     * @param pageSize 每页多少
     * @return
     */
    protected PageData getPageInfo(String pageNumber,String pageSize){
        PageData pd = new PageData();
        if(StringUtils.isBlank(pageSize)){
            pageSize = this.pageSize;
        }
        int start = 0;
        if (StringUtils.isNotBlank(pageNumber)) {
            int pageNum = Integer.parseInt(pageNumber);
            if(pageNum>0){
                start = (pageNum-1)* Integer.parseInt(pageSize);
            }
        }
        pd.put("start", start);
        pd.put("page_size", Integer.parseInt(pageSize));
        return pd;
    }

}
