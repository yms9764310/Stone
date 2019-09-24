package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.mapper.MessageMapper;
import com.jc.mapper.ToDoListMapper;
import com.jc.model.*;
import com.jc.service.MessageService;
import com.jc.service.ToDoListService;
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
    public List<Message> listMessage(String page, String limit, String name, String message_type) {
        PageRange pageRange = new PageRange(page, limit);
        String id = "3";
        List<Message> messageList = messageMapper.listMessageAll(pageRange.getStart(), pageRange.getEnd(),id, name,message_type);
        //查询所有消息列表，把包含自己ID的那些数据抓取出来。
//        for (Message message : messageList) {
//            String to_user_ids = message.getTo_user_ids();
//            String[] split = to_user_ids.split(",");
//            boolean flag = Arrays.asList(split).contains(""+id);
//            if(flag){
//
//            }
//        }

        return messageList;
    }

    @Override
    public int countGetAll() {
        return messageMapper.countGetAll();
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
