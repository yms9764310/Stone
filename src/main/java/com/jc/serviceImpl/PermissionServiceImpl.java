package com.jc.serviceImpl;

import com.jc.mapper.YzjRoleMapper;
import com.jc.mapper.YzjSysAuthorityMapper;
import com.jc.mapper.YzjSysAuthorityRoleMapper;
import com.jc.model.SysRole;
import com.jc.model.YzjSysAuthority;
import com.jc.model.YzjSysAuthorityRole;
import com.jc.service.PermissionService;
import com.jc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private YzjRoleMapper yzjRoleMapper;
    @Autowired
    private YzjSysAuthorityRoleMapper yzjSysAuthorityRoleMapper;
    @Autowired
    private YzjSysAuthorityMapper yzjSysAuthorityMapper;
    @Override
    public Set<String> listPermissionName(String name) {
        Set<String> result = new HashSet<>();
        List<SysRole> roles = yzjRoleMapper.selectByPrimaryKeyName(name);
        List<YzjSysAuthorityRole> rolePermissions = new ArrayList<>();
        for (SysRole role : roles) {
            List<YzjSysAuthorityRole> rps = yzjSysAuthorityRoleMapper.selectListRolePermissionByRID(role.getId());
            rolePermissions.addAll(rps);
        }

        for (YzjSysAuthorityRole rolePermission : rolePermissions) {
            YzjSysAuthority p = yzjSysAuthorityMapper.selectByPrimaryKey(rolePermission.getAuthority_id());
            result.add(p.getValue());
        }
        return result;
    }
}
