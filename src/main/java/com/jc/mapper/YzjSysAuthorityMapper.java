package com.jc.mapper;

import com.jc.model.YzjSysAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YzjSysAuthorityMapper {
    YzjSysAuthority selectByPrimaryKey(@Param("id")int id);
    List<YzjSysAuthority> selectPermissionList();
}
