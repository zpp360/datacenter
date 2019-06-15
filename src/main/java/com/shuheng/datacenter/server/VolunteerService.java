package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("volunteerService")
public class VolunteerService{
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 志愿者列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listVolunteer(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listVolunteer",pd);
        return list;
    }

    /**
     * 志愿者组织列表
     * @param pd
     * @return
     */
    public List<PageData> listVolunteerOrg(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listVolunteerOrg",pd);
        return list;
    }

    /**
     * 志愿者组织和志愿者关联列表
     * @return
     */
    public List<PageData> listVolunteerOrgRelation(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listVolunteerOrgRelation",pd);
        return list;
    }

    /**
     * 志愿者活动列表
     * @param pd
     * @return
     */
    public List<PageData> listVolunteerActive(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listVolunteerActive",pd);
        return list;
    }

    /**
     * 志愿者活动报名详情列表
     * @param pd
     * @return
     */
    public List<PageData> listVolunteerActiveRelation(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listVolunteerActiveRelation",pd);
        return list;
    }
}
