package com.jc.serviceImpl;

import com.jc.mapper.YzjSysLoginUserMapper;
import com.jc.model.SysLoginUser;
import com.jc.model.YzjSysLoginUser;
import com.jc.service.YzjSysLoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
public class YzjSysLoginUserServiceImpl implements YzjSysLoginUserService {
    @Autowired
    YzjSysLoginUserMapper sysLoginUserMapper;



    @Override
    public Boolean login(YzjSysLoginUser yzjSysLoginUser) {
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
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
    public List<String> logout(){
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String account_name = user.getAccount_name();
        List<String> roles = new ArrayList<String>();
        roles.add(account_name);
        return roles;
    }
}
