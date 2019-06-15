package com.shuheng.datacenter.server;


import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cadresAssociationService")
public class CadresAssociationService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 老干部协会列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listCadersAssociation(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CadersAssociationMapper.listCadersAssociation",pd);
    }

    /**
     * 协会成员列表
     * @param pd
     * @return
     */
    public List<PageData> listCadersAssociationUser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CadersAssociationMapper.listCadersAssociationUser",pd);
    }
}
