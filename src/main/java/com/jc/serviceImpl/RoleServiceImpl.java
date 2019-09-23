package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.YzjRoleMapper;
import com.jc.mapper.YzjSysLoginUserMapper;
import com.jc.mapper.YzjUserRoleMapper;
import com.jc.model.SysLoginUser;
import com.jc.model.SysRole;
import com.jc.model.SysUserRole;
import com.jc.service.RoleService;
import com.jc.service.YzjSysLoginUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private YzjSysLoginUserService yzjSysLoginUser;
    @Resource
    private YzjUserRoleMapper yzjUserRoleMapper;
    @Resource
    private YzjRoleMapper yzjRoleMapper;
    @Resource
    private YzjSysLoginUserMapper yzjSysLoginUserMapper;
//分页查询角色
    @Override
    public List<SysRole> listYzjSysRole(String page, String limit,String name,String department) {
        PageRange pageRange = new PageRange(page,limit);
        return yzjRoleMapper.listYzjSysRole(pageRange.getStart(),pageRange.getEnd(),name,department);
    }

    @Override
    public int countYzjSysRole() {
        return yzjRoleMapper.countYzjSysRole();
    }

    @Override
    public Set<String> listRoleName(String account_name) {
        Set<String> result = new HashSet<>();
        List<SysRole> roles = listRoleByName(account_name);
        for (SysRole role : roles) {
            result.add(role.getName());
        }
        return result;
    }
    /**
     * 通过 用户名 获取 他拥有的所有角色
     */

    public List<SysRole> listRoleByName(String account_name) {
        List<SysRole> roles = new ArrayList<>();
        SysLoginUser user = yzjSysLoginUserMapper.loadLoginByName(account_name);
        if (null == user)
            return roles;

        roles = listRoleByUser(user);
        return roles;
    }
/*
添加角色
 */
    @Override
    public Integer saveYzjRole(SysRole sysRole) {
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();//获取当前登录用户id
        int id = user.getId();
        sysRole.setCreator(id);
        sysRole.setCreate_date(date);
        sysRole.setModifier(id);
        sysRole.setModify_date(date);
        sysRole.setDepartment_id(sysRole.getId());
        sysRole.setState("可用");
        yzjRoleMapper.saveYzjSysRole(sysRole);
        return 1;
    }

    @Override
    public int deleteSysRole(int id) {
        return yzjRoleMapper.deleteSysRole(id);
    }
//编辑
    @Override
    public Integer updateRole(SysRole sysRole) {
        Date date = new Date();

        sysRole.setModify_date(date);
        yzjRoleMapper.updateSysRole(sysRole);
        return 0;
    }

    @Override
    public SysRole loadSysRole(int id) {
        return yzjRoleMapper.loadSysRole(id);
    }

    /**
     * 通过 用户 获取 他拥有的所有角色
     */

    public List<SysRole> listRoleByUser(SysLoginUser user) {
        List<SysRole> roles = new ArrayList<>();

        List<SysUserRole> userRoles = yzjUserRoleMapper.getUserRoleByUID(user.getUser_id());

        for (SysUserRole userRole : userRoles) {
            SysRole role = yzjRoleMapper.selectByPrimaryKeyId(userRole.getRole_id());
            roles.add(role);
        }
        return roles;
    }
}
