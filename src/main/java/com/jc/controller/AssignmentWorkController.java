package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysUsers;
import com.jc.model.SysUsersBeans;
import com.jc.model.SysUsersExceptSale;
import com.jc.service.AssignmentWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 22
 * 分钟: 24
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/AssignmentWork")
public class AssignmentWorkController {
    @Resource
    private AssignmentWorkService assignmentWorkServiceImpl;
    /**
     * 修改资料
     * */
    @RequestMapping("/changeMessage")
    @ResponseBody
    public IResult updateMessage(@RequestBody SysUsers sysUsers){
        return new ResultBean<String>(assignmentWorkServiceImpl.updateMessage(sysUsers));
    }

    /**
     * 查询本部门人员
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult loadUsers(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        //先获取当前账号的ID,判断是否是主管
        int id = 1;
        SysUsersBeans sysUsersBeans =assignmentWorkServiceImpl.loadById(id);
        System.out.println(sysUsersBeans.getName());
        System.out.println(sysUsersBeans.getRole_id());
        System.out.println(sysUsersBeans.getDepart_id());
        if(sysUsersBeans.getName().equals("主管")&&sysUsersBeans.getRole_id().equals("1")){
            if(sysUsersBeans.getDepart_id().equals("生产部")){
                return new PageResultBean<Collection<SysUsers>>(assignmentWorkServiceImpl.ListSaleAll(page,limit,name),assignmentWorkServiceImpl.countGetAll());
            }else {
                return new PageResultBean<Collection<SysUsersExceptSale>>(assignmentWorkServiceImpl.ListAll(page,limit,name),assignmentWorkServiceImpl.countGetAll());
            }

        }
        return new ResultBean<Boolean>(false);
    }
}
