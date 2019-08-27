package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.AssignmentWorkService;
import com.jc.service.ToDoListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

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
@RequestMapping("/ToDoList")
public class ToDoListController {
    @Resource
    private ToDoListService toDoListServiceImpl;
    /**
     * 审核
     * */
    @RequestMapping("/reviewSuccess")
    @ResponseBody
    public IResult updateMessage(@RequestBody ToDoList toDoList){
        return new ResultBean<String>(toDoListServiceImpl.updateMessage(toDoList));
    }
    /**
     * 查询待办事项
     * */
    @RequestMapping("/upComing")
    @ResponseBody
    public IResult listProcessAll(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ProduceTask> resultData = toDoListServiceImpl.listProcessAll(page, limit,name);
        return new PageResultBean<Collection<ProduceTask>>(resultData,resultData.size());
    }
    /**
     * 查询工作流记录
     * */
    @RequestMapping("/viewWorkflow")
    @ResponseBody
    public IResult listWorkflow(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ProduceTask> resultData = toDoListServiceImpl.listWorkflow(page, limit,name);
        return new PageResultBean<Collection<ProduceTask>>(resultData,resultData.size());
    }

    /**
     * 查询工作进度
     * */
    @RequestMapping("/viewWorkProgress")
    @ResponseBody
    public IResult listWorkAll(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ProduceTask> resultData = toDoListServiceImpl.listWorkAll(page, limit,name);
        return new PageResultBean<Collection<ProduceTask>>(resultData,resultData.size());
    }
    /**
     * 查询待审核事项
     * */
    @RequestMapping("/find")
    @ResponseBody
    public IResult listAll(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ToDoList> resultData = toDoListServiceImpl.listAll(page,limit,name);
        return new PageResultBean<Collection<ToDoList>>(resultData,resultData.size());
    }

    /**
     * 查看详情
     * */
    @RequestMapping("/get")
    @ResponseBody
    public IResult getMenu(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        ProduceTaskDetail produceTaskDetail = toDoListServiceImpl.loadByProcessId(id);
        return new ResultBean<ProduceTaskDetail>(produceTaskDetail);
    }

    /**
     * 获取工作流记录
     * */
    @RequestMapping("/getWorkFlow")
    @ResponseBody
    public IResult getWorkFlow(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean

       List<ProduceTask> produceTasks = toDoListServiceImpl.listWorkflowHistory(id);
        return new ResultBean<Collection<ProduceTask>>(produceTasks);
    }

    /**
     * 获取工作进度
     * */
    @RequestMapping("/getWorkSpeed")
    @ResponseBody
    public IResult getWorkSpeed(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ProduceTask> produceTasks = toDoListServiceImpl.listWorkflowHistory(id);
        return new ResultBean<Collection<ProduceTask>>(produceTasks);
    }
}
