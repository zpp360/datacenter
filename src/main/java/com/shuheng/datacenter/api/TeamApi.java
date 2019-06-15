package com.shuheng.datacenter.api;

import com.shuheng.datacenter.base.BaseApi;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.TeamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 协会社团
 */
@RestController
public class TeamApi extends BaseApi {

    @Resource(name = "teamService")
    private TeamService teamService;

    /**
     * 协会社团列表接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "teamList")
    public ApiData listTeam(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = teamService.listTeam(pd);
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
     * 团队成员接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "teamUserList")
    public ApiData listTeamUser(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = teamService.listTeamUser(pd);
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
     * 团队招募列表接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "teamRecruitList")
    public ApiData listTeamRecruit(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = teamService.listTeamRecruit(pd);
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
     * 团队招募报名人员列表
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "teamRecruitUserList")
    public ApiData listTeamRecruitUser(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = teamService.listTeamRecruitUser(pd);
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
