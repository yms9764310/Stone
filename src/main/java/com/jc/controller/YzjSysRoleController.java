package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysRole;
import com.jc.model.YzjSysPurchaseProduct;
import com.jc.service.RoleService;
import com.jc.serviceImpl.RoleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/role")
public class YzjSysRoleController {
    @Resource
    RoleService roleServiceimpl;
    @RequestMapping("/listrole")
    @ResponseBody
    public IResult getRoleList(String page, String limit, String name,String department){

        List<SysRole> resultData = roleServiceimpl.listYzjSysRole(page,limit,name,department);
        return new PageResultBean<Collection<SysRole>>(resultData,roleServiceimpl.countYzjSysRole());
    }
    @RequestMapping("/saverole")
    @ResponseBody
    public IResult saverole(@RequestBody SysRole sysRole){
    return new ResultBean<Integer>(roleServiceimpl.saveYzjRole(sysRole));
    }
    @RequestMapping("/deleterole")
    @ResponseBody
    public IResult deleterole(Integer id){
    return new ResultBean<Integer>(roleServiceimpl.deleteSysRole(id));
    }
    //编辑角色
    @RequestMapping("/updaterole")
    @ResponseBody
    public IResult updaterole(@RequestBody SysRole sysRole) {
        return new ResultBean<Integer>(roleServiceimpl.updateRole(sysRole));
    }
    //根据id查询
    @RequestMapping("/loadrole")
    @ResponseBody
    public IResult loadrole(Integer id) {
        return new ResultBean<SysRole>(roleServiceimpl.loadSysRole(id));
    }
}
