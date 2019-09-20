package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.entity.Region;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("regionService")
public class RegionService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public List<PageData> listRegion(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("RegionMapper.listRegion",pd);
    }

    /**
     * 根据id查询
     * @param regionId
     * @return
     */
    public PageData findById(String regionId) throws Exception {
        return (PageData) dao.findForObject("RegionMapper.findById",regionId);
    }

    /**
     * 根据id获取del_flag 为1的数量
     * @param regionId
     * @return
     */
    public Long findByIdAndDelFlag(String regionId) throws Exception {
        return (Long) dao.findForObject("RegionMapper.findByIdAndDelFlag",regionId);
    }

    /**
     * 根据id删除del_flag为1的
     * @param regionId
     */
    public void delDelFlagRegion(String regionId) throws Exception {
        dao.delete("RegionMapper.delDelFlagRegion",regionId);
    }

    /**
     * 批量添加
     * @param list
     */
    public void batchSave(List<Region> list) throws Exception {
        dao.batchSave("RegionMapper.batchSave",list);
    }

    /**
     * 批量更新
     * @param list
     */
    public void batchUpdate(List<Region> list) throws Exception {
        dao.batchSave("RegionMapper.batchUpdate",list);
    }

    /**
     *
     * @param delList
     */
    public void batchDel(List<Region> delList) throws Exception {
        dao.delete("RegionMapper.batchDel",delList);
    }
}
