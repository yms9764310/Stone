package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import com.jc.service.SysLoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 15
 * 分钟: 25
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/SysLoginUser")
public class SysLoginUserController {
    @Resource
    private SysLoginUserService sysLoginUserServiceImpl;
    /**
     * 修改密码+校验密码
     * response code:200 原始密码错误 ；300 修改成功；400 修改失败
     * {data:{},code:300}
     * */
    @RequestMapping("/changePassword")
    @ResponseBody
    public IResult updatePassword(@RequestBody SysLoginUser sysLoginUser){
        //需要去查询当前用户ID
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();//获取当前登录用户id
        int id = user.getId();
        sysLoginUser.setId(id);
        SysLoginUser sysLoginUser1 =sysLoginUserServiceImpl.loadById(sysLoginUser.getId());
        //获取页面上的密码与数据库里的旧密码比较，一致的话修改。
        if(sysLoginUser.getOldPwd().equals(sysLoginUser1.getPsd())){
            sysLoginUserServiceImpl.updatePassword(sysLoginUser);
            int code = 300;
        return new ResultBean<Integer>(code);
        //不一致，报错
        }else {
            int code = 200;
            return new ResultBean<Integer>(code);
        }

    }

    /**
     * 查询
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult loadfindById(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int user_id = user.getId();
        return new ResultBean<SysLoginUser>(sysLoginUserServiceImpl.loadById(user_id));
    }

}
