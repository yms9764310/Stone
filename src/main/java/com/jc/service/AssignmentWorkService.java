package com.jc.service;

import com.jc.model.SysUsers;
import com.jc.model.SysUsersBeans;
import com.jc.model.SysUsersExceptSale;
import org.apache.ibatis.annotations.Param;

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
public interface AssignmentWorkService {
    //修改
    String  updateMessage(SysUsers sysUsers);
    //根据ID查询
    SysUsersBeans loadById(Integer id);
    //根据销售主管的部门ID查询全部
    List<SysUsers> ListSaleAll(@Param("start") String start,
                               @Param("end") String end,
                               @Param("name") String name);
    //根据普通主管的部门ID查询全部
    List<SysUsersExceptSale> ListAll(@Param("start") String start,
                                     @Param("end") String end,
                                     @Param("name") String name);
    //获取条数
    int countGetAll();
}
