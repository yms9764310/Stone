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

    //根据销售模块
    List<Message> listMessage(@Param("start") String start,
                          @Param("end") String end,
                              @Param("name") String name,
                              @Param("message_type") String message_type);
    //获取条数
    int countGetAll();
}
