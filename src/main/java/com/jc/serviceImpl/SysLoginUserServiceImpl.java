package com.jc.serviceImpl;/*
 * @author 林锦杰
 * @date 2019/8/28 9:28
 * */

import com.jc.mapper.sale.SaleCustomerMapper;
import com.jc.mapper.system.SysLoginUserMapper;
import com.jc.model.system.SysLoginUser;
import com.jc.service.system.SysLoginUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SysLoginUserServiceImpl implements SysLoginUserService {
    @Resource
    private SysLoginUserMapper sysLoginUserMapper;
    @Override
    public SysLoginUser loadById(int id) {
        return sysLoginUserMapper.loadById(id);
    }
}
