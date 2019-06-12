package com.shuheng.datacenter.api;

import com.shuheng.datacenter.base.BaseApi;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.VolunteerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 志愿者接口
 */
@RestController
public class VolunteerApi extends BaseApi {

    @Resource(name = "volunteerService")
    private VolunteerService volunteerService;

    /**
     * 志愿者接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/volunteerList")
    public ApiData listVolunteer(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = volunteerService.listVolunteer(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
        }
        return data;
    }

    /**
     * 志愿者组织接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/volunteerOrgList")
    public ApiData listVolunteerOrg(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = volunteerService.listVolunteerOrg(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
            return data;
        }
        return data;
    }

    /**
     * 志愿者组织和志愿者关联接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/volunteerOrgRelationList")
    public ApiData listVolunteerOrgRelation(String timestamp,String pageNumber,String pageSize) {
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber, pageSize);
        if (StringUtils.isNotBlank(timestamp)) {
            pd.put("timestamp", timestamp);
        }
        try {
            List<PageData> list = volunteerService.listVolunteerOrgRelation(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
            return data;
        }
        return data;
    }


    /**
     * 志愿者活动接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/volunteerActiveList")
    public ApiData listVolunteerActive(String timestamp,String pageNumber,String pageSize) {
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber, pageSize);
        if (StringUtils.isNotBlank(timestamp)) {
            pd.put("timestamp", timestamp);
        }
        try {
            List<PageData> list = volunteerService.listVolunteerActive(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
            return data;
        }
        return data;
    }
}
