package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.mapper.MessageMapper;
import com.jc.mapper.ToDoListMapper;
import com.jc.model.*;
import com.jc.service.MessageService;
import com.jc.service.ToDoListService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;


    @Override
    //查看消息通知列表
    public List<Message> listMessage(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.listMessageAll(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        return messageList;
    }


    //查看未读消息通知列表
    public List<Message> noReadList(Integer user_id) {
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.noReadList(id);
        return messageList;
    }

    //查看审核通知列表
    public List<Message> auditList(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.auditList(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        return messageList;
    }

    //查看完结通知列表
    public List<Message> endList(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.endList(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        return messageList;
    }

    //查看部门待办通知列表
    public List<Message> toDoList(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.toDoList(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        return messageList;
    }
    //查看工作分配通知列表
    public List<Message> jobList(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        String id = String.valueOf(user.getId());
        List<Message> messageList = messageMapper.jobList(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        return messageList;
    }

    @Override
    public int countGetAll() {
        int i = messageMapper.countGetAll();
        return i;
    }

    public List<ToDoList> listLimitData(String page, String limit, List<ToDoList> limitData) {
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        //起始下标
        int fromIndex = Integer.valueOf(limit) * (Integer.valueOf(page) - 1);
        //终止下标
        int intLimit = Integer.valueOf(limit);
        int toIndex = fromIndex + intLimit;
        int size = limitData.size();
        if (toIndex >= size) {
            toIndex = size;
        }
        resultData = limitData.subList(fromIndex, toIndex);
        return resultData;
    }
}
