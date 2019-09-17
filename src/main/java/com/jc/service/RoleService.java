package com.jc.service;

import com.jc.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public interface RoleService {
    /**
     * 通过 用户名 获取 他拥有的所有角色名
     */
    List<SysRole>listYzjSysRole(String page, String limit,String name,String department);
    int countYzjSysRole();
    Set<String> listRoleName(String account_name);
    List<SysRole> listRoleByName(String account_name);
    //单独创建
    Integer saveYzjRole(SysRole sysRole);
    //删除
    int deleteSysRole(int id);
    //编辑
    Integer updateRole(SysRole sysRole);
    //根据id查询
    SysRole loadSysRole(int id);
}
