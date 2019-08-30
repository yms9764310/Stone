package com.jc.service;

import com.jc.model.SysLoginUser;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 41
 *
 * @author 严脱兔
 */
public interface SysLoginUserService {
    //修改
    String  updatePassword(SysLoginUser sysLoginUser);
    //根据ID查询
    SysLoginUser loadById(Integer id);
}
