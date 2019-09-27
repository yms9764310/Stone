package com.jc.mapper;

import com.jc.model.Message;
import com.jc.model.SysUsers;
import com.jc.model.UserHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 26
 * 小时: 14
 * 分钟: 32
 *
 * @author 严脱兔
 */
@Repository
public interface PersonLogHistoryMapper {

        //查看全部日志记录
        List<UserHistory> listUserHistoryAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("startTime") String startTime,
                                         @Param("endTime") String endTime,
                                         @Param("name") String name,
                                         @Param("description") String description);
        //获取条数
        int countGetAll();
        //删除日志记录
        void deleteLogHistory(Integer id);
}
