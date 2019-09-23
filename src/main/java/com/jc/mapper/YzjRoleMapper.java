package com.jc.mapper;

import com.jc.model.SysRole;
import com.jc.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YzjRoleMapper {
    List<SysRole>listYzjSysRole(@Param("start") Integer start,
                                @Param("end") Integer end ,@Param("name") String name, @Param("department") String department);
    int countYzjSysRole();
    SysRole selectByPrimaryKeyId(@Param("id")int id);
    List<SysRole> selectByPrimaryKeyName(@Param("name")String  name);

    //添加角色
    Integer saveYzjSysRole(SysRole sysRole);
    //删除
    int deleteSysRole(int id);
    //编辑
    int updateSysRole(SysRole sysRole);
    //根据id查询
    SysRole loadSysRole(int id);
}
