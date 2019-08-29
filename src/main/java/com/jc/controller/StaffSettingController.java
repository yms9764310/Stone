package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.StaffSettingService;
import com.jc.service.SysUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 14
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/StaffSetting")
public class StaffSettingController {
    @Resource
    private StaffSettingService staffSettingServiceImpl;

    /**
     * 获取用户
     * */
    @RequestMapping("/get")
    @ResponseBody
    public IResult getMenu(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        SysUsers sysUsers = staffSettingServiceImpl.loadByUserId(id);
        return new ResultBean<SysUsers>(sysUsers);
    }

    /**
     * 修改阈值
     *  300 修改成功；400 修改失败
     * */
    @RequestMapping("/changeThreshold")
    @ResponseBody
    public IResult updateThreshold(@RequestBody SysUsers sysUsers){
        return new ResultBean<Integer>(staffSettingServiceImpl.updateThreshold(sysUsers));
    }
    /**
     * 修改资料
     *  300 修改成功；400 修改失败
     * */
    @RequestMapping("/changeMessage")
    @ResponseBody
    public IResult updateMessage(@RequestBody SysUsers sysUsers){

        return new ResultBean<Integer>(staffSettingServiceImpl.updateMessage(sysUsers));
    }

    /**
     * 查询本部门人员
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult loadUsers(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<SysUsers> sysUsers = staffSettingServiceImpl.loadUsers(page, limit, name);
        return new PageResultBean<Collection<SysUsers>>(sysUsers,sysUsers.size());
    }
}
