package com.jc.mapper;

import com.jc.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YzjUserRoleMapper {
        List<SysUserRole>getUserRoleByUID(@Param("user_id")int user_id);

}
