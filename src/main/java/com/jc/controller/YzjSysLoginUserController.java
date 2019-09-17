package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.ResultBean;
import com.jc.model.YzjSysLoginUser;
import com.jc.service.YzjSysLoginUserService;
import com.jc.serviceImpl.YzjSysLoginUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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

public class YzjSysLoginUserController {
    @Resource
    private YzjSysLoginUserService sysLoginUserService;
    @RequestMapping("/login")
    @ResponseBody
    public IResult login(@RequestBody YzjSysLoginUser yzjSysLoginUser){
        return new ResultBean<Boolean>(sysLoginUserService.login(yzjSysLoginUser));
    }
    @RequestMapping("/logout")
    @ResponseBody
    public IResult  logout(){

       return new ResultBean<List<String >>(sysLoginUserService.logout());
    }



}
