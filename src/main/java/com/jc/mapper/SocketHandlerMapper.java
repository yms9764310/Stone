package com.jc.mapper;

import com.jc.model.Message;
import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 29
 * 小时: 16
 * 分钟: 02
 *
 * @author 严脱兔
 */
@Repository
public interface SocketHandlerMapper {
    SysLoginUser loadUser(@Param("user_id")String user_id);

    void insertMessage(Message message);
}
