package com.jc.serviceImpl;

import com.jc.mapper.ToDoListMapper;
import com.jc.mapper.UserHistoryMapper;
import com.jc.model.UserHistory;
import com.jc.service.UserHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 年: 2019
 * 月: 09
 * 日: 12
 * 小时: 15
 * 分钟: 18
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class UserHistoryServiceImpl implements UserHistoryService {
    @Resource
    private UserHistoryMapper userHistoryMapper;

    @Override
    public void save(UserHistory userHistory) throws Exception {
        userHistoryMapper.save(userHistory);
    }

}
