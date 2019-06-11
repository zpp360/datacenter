package com.shuheng.datacenter.api;

import com.shuheng.datacenter.base.BaseApi;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.RegionService;
import com.shuheng.datacenter.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class RegionInfoApi extends BaseApi {

    @Resource(name = "regionService")
    private RegionService regionService;

    /**
     * 增量同步regionInfo（地区）接口,如果timestamp为空，则是全量
     * @return
     */
    @PostMapping(value = "/regionList")
    public ApiData regionList(String timestamp,String pageNumber,String pageSize){
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        ApiData apiData = new ApiData();
        if(StringUtils.isNotBlank(timestamp)){
            if(!DateUtil.isValidDate(timestamp)){
                //日期不合法
                apiData.setErrorCode(ApiConstants.CODE_204);
                return apiData;
            }
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = regionService.listRegion(pd);
            apiData.setDatas(list);
            apiData.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            apiData.setErrorCode(ApiConstants.CODE_203);
            return apiData;
        }
        return apiData;
    }

}
