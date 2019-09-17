package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageRange;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.OrgNodeResponse;
import com.jc.model.YzjSysDepartmentTest;
import com.jc.model.YzjSysLoginUser;
import com.jc.service.YzjSysDepartmentTestService;
import com.jc.service.YzjSysLoginUserTestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/demp")
public class YzjSysDepartmentController {
    @Resource
    private YzjSysDepartmentTestService sysDepartmentTestServiceImpl;
    @Resource
    private YzjSysLoginUserTestService sysLoginUserTestServiceImpl;

    @RequestMapping("/dempList")
    @ResponseBody
    @RequiresPermissions("list:select")
    //分页查询
    public IResult getDempList(String page,String limit,String name){

        List<YzjSysDepartmentTest> resultData = sysDepartmentTestServiceImpl.listDepartmet(page,limit,name);
        return new PageResultBean<Collection<YzjSysDepartmentTest>>(resultData,sysDepartmentTestServiceImpl.countDepartmet());
    }


    //删除
    @RequestMapping("/deletedemp")
    @ResponseBody
    public IResult deleteDemp(int id){
        return new ResultBean<Integer>(sysDepartmentTestServiceImpl.deleteDepartment(id));
    }


    //根据id查询
    @RequestMapping("/loaddemp")
    @ResponseBody
    public IResult loadDemp(int id){
        //获取编辑页面的值
        return new ResultBean<YzjSysDepartmentTest>(sysDepartmentTestServiceImpl.loadDepartmentById(id));
    }



    //添加单个部门信息
    @RequestMapping("/savedemp")
    @ResponseBody
    public IResult saveDemp(@RequestBody YzjSysDepartmentTest yzjSysDepartmentTest){
        return new ResultBean<Integer>(sysDepartmentTestServiceImpl.saveDepartmentTest(yzjSysDepartmentTest));
    }

    //编辑部门
    @RequestMapping("/updatedemp")
    @ResponseBody
    public IResult updateDemp(@RequestBody YzjSysDepartmentTest yzjSysDepartmentTest){
        return new ResultBean<Integer>(sysDepartmentTestServiceImpl.updateDepartment(yzjSysDepartmentTest));
    }


    //下拉框
    @RequestMapping("/listdempname")
    @ResponseBody
    public IResult listDempName(){
        //获取下拉框的值
       List<YzjSysDepartmentTest> yzjSysDepartmentTestName= sysDepartmentTestServiceImpl.listDepartmetName();
        return new ResultBean<List>(yzjSysDepartmentTestName);
    }



    //更新下拉框
    @RequestMapping("/listdempbyid")
    @ResponseBody
    public IResult listDempById(int id){
        //获取更新下拉框的值
       List<YzjSysDepartmentTest> yzjSysDepartmentTestById= sysDepartmentTestServiceImpl.listDepartmetById(id);
        return new ResultBean<List>(yzjSysDepartmentTestById);
    }
    /**
     * 批量上传
     * */
    @ResponseBody
    @RequestMapping(value="/fileUpload.do")
    public IResult UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ResultBean<String>(sysDepartmentTestServiceImpl.listDepartmentExcle(request,response));
    }
    /**
     * 获取所有组织机构
     * */
    @RequestMapping(value = "/All.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getAll(){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<OrgNodeResponse>>(sysDepartmentTestServiceImpl.listDepartmentParentId());
    }


}
