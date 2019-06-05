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

    public List<PageData> listAll(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("VolunteerMapper.listAll",pd);
        return list;
    }

}
