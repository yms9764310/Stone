package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.mapper.SysUsersMapper;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import com.jc.service.SysUsersService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class SysUsersServiceImpl implements SysUsersService {
    @Resource
    private SysUsersMapper sysUsersMapper;

    public String updateMessage(SysUsers sysUsers) {
        sysUsersMapper.updateMessage(sysUsers);
        return "success";
    }

    public SysUsers loadById(Integer id) {
        return sysUsersMapper.loadById(id);
    }

    @Override
    public List<SysUsers> listSysUsers() {
        return sysUsersMapper.listSysUsers();
    }

    @Override
    public List<SysUsers> listUserAll(String page, String limit) {
        PageRange pageRange = new PageRange(page, limit);
        return sysUsersMapper.listUserAll(pageRange.getStart(),pageRange.getEnd());
    }

    @Override
    public int countGetAll() {
        return sysUsersMapper.countGetAll();
    }

    @Override
    public Integer saveSysUser(SysUsers sysUsers) {
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();//获取当前登录用户id
        int id = user.getId();
        sysUsers.setCreator_id(id);
        sysUsers.setCreate_date(date);
        sysUsers.setModifier_id(id);
        sysUsers.setModify_date(date);
        sysUsers.setState("未冻结");
        sysUsersMapper.saveSysUser(sysUsers);
        return 1;
    }
}
