package com.jc.mapper;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface StaffSettingMapper {
        //根据ID查询
        SysUsersBeans loadById(Integer id);
        //根据用户ID查询
        SysUsers loadByUserId(Integer id);
        //根据主管的部门ID查询全部
        List<SysUsers> listSaleAll(@Param("start") Integer start,
                               @Param("end") Integer end,
                               @Param("name") String name,
                                   @Param("department_id") String department_id);
        //根据普通主管的部门ID查询全部
        List<SysUsers> listAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name,
                               @Param("department_id") String department_id);
        //根据主管的部门ID查询全部
        List<SysUsers> listAllUsers(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name,
                                    @Param("department_id") String department_id);
        //修改信息
        void updateMessage(SysUsers sysUsers);
        //修改阈值
        void  updateThreshold(SysUsers sysUsers);
        //获取条数
        int countGetAll();
}
