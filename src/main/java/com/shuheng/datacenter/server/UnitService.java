package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("unitService")
public class UnitService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void batchSave(List<PageData> list) throws Exception {
        dao.batchSave("UnitMapper.batchSave",list);
    }

}
