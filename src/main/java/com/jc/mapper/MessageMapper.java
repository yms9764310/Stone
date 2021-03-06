package com.jc.mapper;

import com.jc.model.Message;
import com.jc.model.SysUsers;
import com.jc.model.ToDoList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 15
 * 分钟: 27
 *
 * @author 严脱兔
 */
@Repository
public interface MessageMapper {
        //根据ID查询
        SysUsers loadById(Integer id);
        //修改信息
        void updateMessage(SysUsers sysUsers);


        //查看全部消息
        List<Message> listMessageAll(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("id") String id,
                                  @Param("name") String name,
                                  @Param("message_type") String message_type);

        //查看全部未读消息
        List<Message> noReadList(@Param("id") String id);

        //查看审核通知列表
        List<Message> auditList(@Param("start") Integer start,
                                     @Param("end") Integer end,
                                     @Param("id") String id,
                                     @Param("name") String name,
                                     @Param("message_type") String message_type);

        //查看完结通知列表
        List<Message> endList(@Param("start") Integer start,
                                @Param("end") Integer end,
                                @Param("id") String id,
                                @Param("name") String name,
                                @Param("message_type") String message_type);

        //查看部门待办通知列表
        List<Message> toDoList(@Param("start") Integer start,
                              @Param("end") Integer end,
                              @Param("id") String id,
                              @Param("name") String name,
                              @Param("message_type") String message_type);

        //查看工作分配通知列表
        List<Message> jobList(@Param("start") Integer start,
                               @Param("end") Integer end,
                               @Param("id") String id,
                               @Param("name") String name,
                               @Param("message_type") String message_type);
        //获取条数
        int countGetAll();
}
