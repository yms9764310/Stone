package com.jc.service;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 14
 * 分钟: 42
 *
 * @author 严脱兔
 */
public interface StaffSettingService {
    //修改信息
    int  updateMessage(SysUsers sysUsers);
    //修改阈值
    int  updateThreshold(SysUsers sysUsers);
    //根据ID查询
    SysUsersBeans loadById(Integer id);
    //根据用户ID查询
    SysUsers loadByUserId(Integer id);

    //查询全部
    List<SysUsers> loadUsers(@Param("start") String start,
                                @Param("end") String end,
                                @Param("name") String name);
    //获取条数
    int countGetAll();
}
