package com.jc.mapper;

import com.jc.model.SysLoginUser;
import org.springframework.stereotype.Repository;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 15
 * 分钟: 27
 *
 * @author 严脱兔
 */
@Repository
public interface SysLoginUserMapper {
        SysLoginUser loadById(Integer id);
        void updatePassword(SysLoginUser sysLoginUser);
}
