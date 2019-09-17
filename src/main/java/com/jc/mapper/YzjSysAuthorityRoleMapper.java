package com.jc.mapper;

import com.jc.model.YzjSysAuthorityRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YzjSysAuthorityRoleMapper {
    List<YzjSysAuthorityRole> selectListRolePermissionByRID(@Param("role_id")int role_id);
}
