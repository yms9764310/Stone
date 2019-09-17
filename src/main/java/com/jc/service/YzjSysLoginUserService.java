package com.jc.service;

import                                                                                                                                                                                                                                                com.jc.model.SysLoginUser;
import com.jc.model.YzjSysLoginUser;

import java.util.List;

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

    Boolean login(YzjSysLoginUser yzjSysLoginUser);
    List<String> logout();
}
