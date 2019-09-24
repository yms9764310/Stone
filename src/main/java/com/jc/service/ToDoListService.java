package com.jc.service;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
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
public interface ToDoListService {
    //修改
    String  updateMessage(ToDoList toDoList);
    //根据ID查询
    SysUsersBeans loadById(Integer id);
    //根据加工单ID查询
    ProduceTaskDetail loadByProcessId(Integer id);

    //根据生产订单
    List<ProduceTask> listProcessAll(@Param("start") String start,
                                        @Param("end") String end,
                                        @Param("creator") String name);
    //查看工作进度
    List<ProduceTask> listWorkAll(@Param("start") String start,
                                  @Param("end") String end,
                                  @Param("creator") String creator);
    //查看工作流记录
    List<ProduceTask> listWorkflowHistory(Integer id);

    //查看工作进度
    List<ProduceTask> listWorkSpeed(Integer id);

    //查看工作流
    List<ProduceTask> listWorkflow(@Param("start") String start,
                                   @Param("end") String end,
                                   @Param("name") String name);

    //根据销售模块
    List<ToDoList> listAll(@Param("start") String start,
                               @Param("end") String end,
                               @Param("creator") String name);

    //获取条数
    int countGetAll();
}
