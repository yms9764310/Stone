package com.jc.mapper;

import com.jc.model.SysUsers;
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
public interface SysUsersMapper {
        //根据ID查询
        SysUsers loadById(Integer id);
        //修改信息
        void updateMessage(SysUsers sysUsers);
        List<SysUsers> listSysUsers();
        SysUsers listSysUser(int id);
        List<SysUsers> listUserAll(@Param("start") Integer start,
                                   @Param("end") Integer end);
    //获取条数
    int countGetAll();
    //添加人员
    Integer saveSysUser(SysUsers sysUsers);
}
