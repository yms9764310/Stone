package com.jc.service;

import                                                                                                                                                                                                                                                com.jc.model.SysLoginUser;


import java.util.List;
import java.util.Set;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 41
 *
 * @author
 */
public interface YzjSysLoginUserService {

    Set<String> login(SysLoginUser yzjSysLoginUser);
    Set<String> logout();
}
