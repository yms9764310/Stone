package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import com.jc.service.SysLoginUserService;
import com.jc.service.SysUsersService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/SysUser")
public class SysUsersController {
    @Resource
    private SysUsersService sysUsersServiceImpl;
    /**
     * 修改资料
     * */
    @RequestMapping("/changeMessage")
    @ResponseBody
    public IResult updateMessage(@RequestBody SysUsers sysUsers){
        return new ResultBean<String>(sysUsersServiceImpl.updateMessage(sysUsers));
    }

    /**
     * 查询
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult loadfindById(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        id = 1;
        return new ResultBean<SysUsers>(sysUsersServiceImpl.loadById(id));
    }
}
