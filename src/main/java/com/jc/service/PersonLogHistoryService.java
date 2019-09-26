package com.jc.service;

import com.jc.model.UserHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 26
 * 小时: 14
 * 分钟: 36
 *
 * @author 严脱兔
 */

public interface PersonLogHistoryService {

    //查看全部日志记录
    List<UserHistory> listUserHistoryAll(String page, String limit, String startTime, String endTime,String name, String description);
    //获取条数
    int countGetAll();
    //删除日志记录
    int deleteLogHistory(Integer id);
}
