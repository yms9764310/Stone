package com.jc.service;

import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
import org.apache.ibatis.annotations.Param;

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
public interface SysUsersService {
    //修改
    String  updateMessage(SysUsers sysUsers);
    //根据ID查询
    SysUsers loadById(Integer id);
    List<SysUsers> listSysUsers();
    List<SysUsers> listUserAll(@Param("start") String start,
                               @Param("end") String end);
    //获取条数
    int countGetAll();
    //添加人员
    Integer saveSysUser(SysUsers sysUsers);
}
