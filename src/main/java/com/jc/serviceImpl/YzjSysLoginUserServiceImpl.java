package com.jc.serviceImpl;

import com.jc.mapper.YzjRoleMapper;
import com.jc.mapper.YzjSysLoginUserMapper;
import com.jc.mapper.YzjUserRoleMapper;
import com.jc.model.SysLoginUser;

import com.jc.model.SysRole;
import com.jc.model.SysUserRole;
import com.jc.service.PermissionService;
import com.jc.service.RoleService;
import com.jc.service.YzjSysLoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
public class YzjSysLoginUserServiceImpl implements YzjSysLoginUserService {
    @Autowired
    YzjSysLoginUserMapper sysLoginUserMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private YzjUserRoleMapper yzjUserRoleMapper;
    @Autowired
    private YzjRoleMapper yzjRoleMapper;


    @Override
    public Set<String> login(SysLoginUser yzjSysLoginUser) {
        //当前用户
       // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Subject currentUser = SecurityUtils.getSubject();
        String account_name = yzjSysLoginUser.getAccount_name();
        String psd = yzjSysLoginUser.getPsd();

        UsernamePasswordToken token = new UsernamePasswordToken(account_name, psd);

        try {

            currentUser.login(token);
            Session session = currentUser.getSession();
            session.setAttribute("currentUser", account_name);
            SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();//获取当前登录用户id
            //获取账号的角色和权限信息
            Set<String> roles = roleService.listRoleName(account_name);
            int user_id = user.getUser_id();
            List<SysUserRole> role = yzjUserRoleMapper.getUserRoleByUID(user_id);
            SysRole roled = new SysRole();
            for (SysUserRole sysUserRole : role) {
                roled = yzjRoleMapper.selectByPrimaryKeyId(sysUserRole.getRole_id());
            }
            String name = roled.getName();
            Set<String> permissions = permissionService.listPermissionName(name);

            return permissions;
        } catch (AuthenticationException e) {
            Set<String> fals = new ManagedSet<>();
            fals.add("false");
            return fals;
        }
    }
    public Set<String> logout(){
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysRole sysRole = yzjRoleMapper.selectByPrimaryKeyId(id);
        String rolename = sysRole.getName();
        Set<String> permissions = permissionService.listPermissionName(rolename);

        return permissions;
    }
}
