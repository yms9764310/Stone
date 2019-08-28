package com.jc.serviceImpl;


import com.jc.mapper.SysUsersMapper;
import com.jc.model.SysUsers;
import com.jc.service.SysUsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class SysUsersServiceImpl implements SysUsersService {
    @Resource
    private SysUsersMapper sysUsersMapper;

    public String updateMessage(SysUsers sysUsers) {
        sysUsersMapper.updateMessage(sysUsers);
        return "success";
    }

    public SysUsers loadById(Integer id) {
        return sysUsersMapper.loadById(id);
    }
}
