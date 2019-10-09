package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.PersonLogHistoryMapper;
import com.jc.model.UserHistory;
import com.jc.service.PersonLogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 26
 * 小时: 14
 * 分钟: 37
 *
 * @author 严脱兔
 */
@Service
public class PersonLogHistoryServiceImpl implements PersonLogHistoryService {
    @Autowired
    PersonLogHistoryMapper personLogHistoryMapper;

    @Override
    public List<UserHistory> listUserHistoryAll(String page, String limit,String startTime, String endTime, String name, String description) {
        PageRange pageRange = new PageRange(page, limit);
        List<UserHistory> resultData = personLogHistoryMapper.listUserHistoryAll(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name, description);
        return resultData;
    }

    @Override
    public int countGetAll() {
        int sumHistoryNum = personLogHistoryMapper.countGetAll();
        return sumHistoryNum;
    }

    @Override
    public int deleteLogHistory(Integer id) {
        personLogHistoryMapper.deleteLogHistory(id);
        return id;
    }
}
