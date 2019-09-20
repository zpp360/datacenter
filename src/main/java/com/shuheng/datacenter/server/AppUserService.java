package com.shuheng.datacenter.server;

import com.shuheng.datacenter.dao.DaoSupport;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.entity.User;
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

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    public PageData findById(String userId) throws Exception {
        return (PageData) dao.findForObject("AppUserMapper.findById",userId);
    }

    /**
     * 批量插入
     * @param list
     */
    public void batchSave(List<User> list) throws Exception {
        dao.batchSave("AppUserMapper.batchSave",list);
    }

    /**
     * 批量更新
     * @param list
     */
    public void batchUpdate(List<User> list) throws Exception {
        dao.batchSave("AppUserMapper.batchUpdate",list);
    }

    /**
     * 批量删除
     * @param delList
     */
    public void batchDel(List<User> delList) throws Exception {
        dao.delete("AppUserMapper.batchDel",delList);
    }

    /**
     * 根据手机号查询用户
     * @param mobilephone
     * @return
     */
    public PageData findByPhone(String mobilephone) throws Exception {
        return (PageData) dao.findForObject("AppUserMapper.findByPhone",mobilephone);
    }

    /**
     * 根据身份证号码查询用户
     * @param cardNumber
     * @return
     */
    public PageData findByCardNumber(String cardNumber) throws Exception {
        return (PageData) dao.findForObject("AppUserMapper.findByCardNumber",cardNumber);
    }

    /**
     * 批量居住地报到
     * @param reportList
     */
    public void batchReport(List<User> reportList) throws Exception {
        dao.batchSave("AppUserMapper.batchReport",reportList);
    }

    /**
     * 根据id查询，del_flag为1
     * @param userId
     * @return
     */
    public Long findByIdAndDelFlag(String userId) throws Exception {
        return (Long) dao.findForObject("AppUserMapper.findByIdAndDelFlag",userId);
    }

    /**
     * 删除del_flag为1的
     * @param userId
     */
    public void delDelFlagUser(String userId) throws Exception {
        dao.delete("AppUserMapper.delDelFlagUser",userId);
    }

    /**
     * 当日增加的用户
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listAddUser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AppUserMapper.listAddUser",pd);
    }
}
