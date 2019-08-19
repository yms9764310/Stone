package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.Student;
import com.jc.service.StudentService;
import com.jc.serviceImpl.StudentServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 44
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/Student")
public class StudentController {
    @Resource
    private StudentService studentServiceImpl;
    /**
     * 查询全部学生
     */
    @RequestMapping("/get")
    @ResponseBody
    public IResult findAll(String page, String limit,String sname){
        return new PageResultBean<Collection<Student>>(studentServiceImpl.findAll(page,limit,sname),studentServiceImpl.countGetAll());
    }
    /**111111111111111111111111
     * 删除学生
     */
    @RequestMapping("/delete")
    @ResponseBody
    public IResult deleteStudent(int sid){
        return new ResultBean<Integer>(studentServiceImpl.delStudent(sid));
    }
    /**
     * 修改学生
     */
    @RequestMapping("/update")
    @ResponseBody
    public IResult updateStudent(@RequestBody Student student){
        return new ResultBean<String>(studentServiceImpl.updateStudent(student));
    }

    /**
     * 添加学生
     */
    @RequestMapping("add")
    @ResponseBody
    public IResult addStudent(@RequestBody Student student){
        return new ResultBean<String>(studentServiceImpl.addStudent(student));
    }
    /**
     * 获取用户
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult getMenu(int sid){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Student>(studentServiceImpl.findById(sid));
    }
}
