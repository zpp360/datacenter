package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("appService")
public class APPService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 根据appId获取appScreate
     * @param appId
     * @return
     * @throws Exception
     */
    public String getAppScreateByAppId(String appId) throws Exception {
        return (String) dao.findForObject("AppMapper.getAppScreateByAppId",appId);
    }
}
