package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appUserService")
public class AppUserService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> listUser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AppUserMapper.listUser",pd);
    }
}
