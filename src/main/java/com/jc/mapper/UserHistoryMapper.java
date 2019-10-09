package com.jc.mapper;

import com.jc.model.UserHistory;
import org.apache.ibatis.annotations.Insert;

/**
 * 年: 2019
 * 月: 09
 * 日: 12
 * 小时: 15
 * 分钟: 21
 *
 * @author 严脱兔
 */
public interface UserHistoryMapper {
    @Insert("insert into t_user_history(modify_date,description,user_id) values(#{modify_date},#{description},#{user_id})")
    public void save(UserHistory userHistory) throws Exception;
}
