package com.jc.mapper;

import com.jc.model.SysUsers;
import com.jc.model.SysUsersBeans;
import com.jc.model.SysUsersExceptSale;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 22
 * 分钟: 24
 *
 * @author 严脱兔
 */
@Repository
public interface AssignmentWorkMapper {
        //根据ID查询
        SysUsersBeans loadById(Integer id);
        //根据主管的部门ID查询全部
        List<SysUsers> ListSaleAll(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);
        //根据普通主管的部门ID查询全部
        List<SysUsersExceptSale> ListAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name);
        //修改信息
        void updateMessage(SysUsers sysUsers);
        //获取条数
        int countGetAll();
}
