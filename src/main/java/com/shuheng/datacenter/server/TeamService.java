package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("teamService")
public class TeamService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 社团协会列表
     * @param pd
     * @return
     */
    public List<PageData> listTeam(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TeamMapper.listTeam",pd);
    }

    /**
     * 团队成员列表
     * @param pd
     * @return
     */
    public List<PageData> listTeamUser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TeamMapper.listTeamUser",pd);
    }

    /**
     * 团队招募列表
     * @param pd
     * @return
     */
    public List<PageData> listTeamRecruit(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TeamMapper.listTeamRecruit",pd);
    }

    /**
     * 团队招募人员列表
     * @param pd
     * @return
     */
    public List<PageData> listTeamRecruitUser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TeamMapper.listTeamRecruitUser",pd);
    }
}
