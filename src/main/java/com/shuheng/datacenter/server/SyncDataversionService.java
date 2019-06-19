package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("syncDataversionService")
public class SyncDataversionService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public String findDataVersionByNameAndMethod(String systemName, String methodName) throws Exception {
        PageData pd = new PageData();
        pd.put("system_name",systemName);
        pd.put("method_name",methodName);
        return (String) dao.findForObject("SyncDataversionMapper.findDataVersionByNameAndMethod",pd);
    }

    public void updateDataVersion(String systemName, String methodName, String timestamp) throws Exception {
        PageData pd = new PageData();
        pd.put("system_name",systemName);
        pd.put("method_name",methodName);
        pd.put("dataversion",timestamp);
        dao.update("SyncDataversionMapper.updateDataVersion",pd);
    }
}
