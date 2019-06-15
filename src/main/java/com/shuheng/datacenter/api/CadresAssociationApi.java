package com.shuheng.datacenter.api;

import com.shuheng.datacenter.base.BaseApi;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.CadresAssociationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老干部协会接口
 */
@RestController
public class CadresAssociationApi extends BaseApi {

    @Resource(name = "cadresAssociationService")
    private CadresAssociationService cadresAssociationService;


    /**
     * 老干部协会同步接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "cadersAssociationList")
    public ApiData listCadersAssociation(String timestamp, String pageNumber, String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = cadresAssociationService.listCadersAssociation(pd);
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
     * 协会成员列表同步接口
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "listCadersAssociationUser")
    public ApiData listCadersAssociationUser(String timestamp, String pageNumber, String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }
        try {
            List<PageData> list = cadresAssociationService.listCadersAssociationUser(pd);
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
