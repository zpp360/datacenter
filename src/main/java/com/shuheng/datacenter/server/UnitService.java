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

    /**
     * 获取青岛所有单位
     * @param param
     * @return
     */
    public List<PageData> listByCity(PageData param) throws Exception {
        return (List<PageData>) dao.findForList("UnitMapper.listByCity",param);
    }

    /**
     * 批量更新unit_path
     * @param unitList
     */
    public void batchUpdateUnitPath(List<PageData> unitList) throws Exception {
        dao.update("UnitMapper.batchUpdateUnitPath",unitList);
    }

    /**
     * 查询单位下APP用户数量
     * @param unitId
     * @return
     */
    public long countAppUserByUnitId(String unitId) throws Exception {
        return (long) dao.findForObject("UnitMapper.countAppUserByUnitId",unitId);
    }

    /**
     * 删除单位
     * @param delList
     */
    public void batchDelete(List<Unit> delList) throws Exception {
        dao.delete("UnitMapper.batchDelete",delList);
    }

    /**
     * 根据单位名称查询
     * @param pd
     * @return
     */
    public List<PageData> listByName(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("UnitMapper.listByName",pd);
    }
}
