package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.model.YzjSysAuthority;

import com.jc.service.YzjSysAuthorityService;
import com.jc.serviceImpl.YzjSysAuthorityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/permissions")
public class YzjSysAuthorityController {
    @Autowired
    private YzjSysAuthorityService yzjSysAuthorityServiceimpl;
    @RequestMapping("/listPermission")
    @ResponseBody

    //分页查询
    public IResult getPermissions(String page, String limit){

        List<YzjSysAuthority> resultData = yzjSysAuthorityServiceimpl.selectByPrimary(page,limit);
        return new PageResultBean<Collection<YzjSysAuthority>>(resultData,resultData.size());
    }
}
