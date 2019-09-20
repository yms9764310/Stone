package com.jc.mapper;

import com.jc.model.SysLoginUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface YzjSysLoginUserMapper {
   SysLoginUser loadLoginByName(@Param("account_name")String account_name);
    SysLoginUser getUserByName(@Param("account_name")String account_name);
}
