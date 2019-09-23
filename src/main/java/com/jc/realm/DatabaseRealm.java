package com.jc.realm;

import com.jc.mapper.YzjRoleMapper;
import com.jc.mapper.YzjSysLoginUserMapper;
import com.jc.mapper.YzjUserRoleMapper;
import com.jc.model.SysLoginUser;
import com.jc.model.SysRole;
import com.jc.model.SysUserRole;
import com.jc.service.PermissionService;
import com.jc.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {
    @Autowired
    private YzjSysLoginUserMapper sysLoginUserMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private YzjUserRoleMapper yzjUserRoleMapper;
    @Autowired
    private YzjRoleMapper yzjRoleMapper;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取输入用户的账号和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String account_name = upToken.getPrincipal().toString();
        SysLoginUser user = sysLoginUserMapper.loadLoginByName(account_name);
        String psd = user.getPsd();
        // 通过 applicationContext-shiro.xml 里配置的 HashedCredentialsMatcher 进行自动校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,psd,getName());

        return authenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 能进入到这里，表示账号已经通过验证了
        System.out.println(principalCollection);
        SysLoginUser user  = (SysLoginUser) principalCollection.getPrimaryPrincipal();

        //获取账号的角色和权限信息
        String account_name = user.getAccount_name();
        Set<String> roles = roleService.listRoleName(account_name);

        int user_id = user.getUser_id();
        List<SysUserRole> role = yzjUserRoleMapper.getUserRoleByUID(user_id);
        SysRole roled = new SysRole();
        for (SysUserRole sysUserRole : role) {
            roled = yzjRoleMapper.selectByPrimaryKeyId(sysUserRole.getRole_id());
        }
            String name = roled.getName();
            Set<String> permissions = permissionService.listPermissionName(name);
            // 授权对象信息
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setStringPermissions(permissions);
            authorizationInfo.setRoles(roles);

        return authorizationInfo;
    }
}
