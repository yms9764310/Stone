package com.jc.mapper.system;/*
 * @author 林锦杰
 * @date 2019/8/28 9:27
 * */

import com.jc.model.system.SysLoginUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLoginUserMapper {
    SysLoginUser loadById(int id);
}
