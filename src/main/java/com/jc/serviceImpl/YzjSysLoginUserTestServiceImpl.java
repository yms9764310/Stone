package com.jc.serviceImpl;

import com.jc.mapper.YzjSysLoginUserTestMapper;
import com.jc.model.YzjSysLoginUser;
import com.jc.service.YzjSysLoginUserTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class YzjSysLoginUserTestServiceImpl implements YzjSysLoginUserTestService {
    @Resource
    private YzjSysLoginUserTestMapper yzjSysLoginUserTestMapper;
    @Override
    public YzjSysLoginUser loadLoginUserById(int id) {
        return yzjSysLoginUserTestMapper.loadLoginUserById(id);
    }
}
