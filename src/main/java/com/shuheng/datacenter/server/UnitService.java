package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.entity.Unit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("unitService")
public class UnitService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void batchSave(List<Unit> list) throws Exception {
        dao.batchSave("UnitMapper.batchSave",list);
    }

    public PageData findById(String unitId) throws Exception {
        return (PageData) dao.findForObject("UnitMapper.findById",unitId);
    }

    public void batchUpdate(List<Unit> list) throws Exception {
        dao.update("UnitMapper.batchUpdate",list);
    }
}
