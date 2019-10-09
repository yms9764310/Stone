package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import com.jc.service.SysUsersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
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
@RequestMapping("/SysUser")
public class YzjSysUsersController {
    @Resource
    private SysUsersService sysUsersServiceImpl;
    /**
     * 查询全部
     */


    @RequestMapping("/listUser")
    @ResponseBody
    public IResult listAllUser(String page,String limit){
        List<SysUsers> sysUsers = sysUsersServiceImpl.listUserAll(page,limit);
        return new PageResultBean<Collection<SysUsers>>(sysUsers,sysUsersServiceImpl.countGetAll());
    }
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
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int user_id = user.getId();
        return new ResultBean<SysUsers>(sysUsersServiceImpl.loadById(user_id));
    }
    /**
     * 添加人员
     */
    @RequestMapping("/saveuser")
    @ResponseBody
    public IResult saveuser(@RequestBody SysUsers sysUsers){
        return new ResultBean<Integer>(sysUsersServiceImpl.saveSysUser(sysUsers));
    }
}
