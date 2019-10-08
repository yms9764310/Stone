package com.jc.service;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;

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
public interface MessageService {

    //全部消息列表
    List<Message> listMessage(@Param("start") String start,
                          @Param("end") String end,
                              @Param("name") String name,
                              @Param("message_type") String message_type);


    //全部未读消息列表
    List<Message> noReadList( @Param("name") Integer id);

    //审核通知列表
    List<Message> auditList(@Param("start") String start,
                              @Param("end") String end,
                              @Param("name") String name,
                              @Param("message_type") String message_type);


    //完结通知列表
    List<Message> endList(@Param("start") String start,
                            @Param("end") String end,
                            @Param("name") String name,
                            @Param("message_type") String message_type);

    //查看部门待办通知列表
    List<Message> toDoList(@Param("start") String start,
                          @Param("end") String end,
                          @Param("name") String name,
                          @Param("message_type") String message_type);


    //查看工作分配通知列表
    List<Message> jobList(@Param("start") String start,
                           @Param("end") String end,
                           @Param("name") String name,
                           @Param("message_type") String message_type);
    //获取条数
    int countGetAll();
}
