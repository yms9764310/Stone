package com.jc.service;

import com.jc.model.YzjSysAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YzjSysAuthorityService {
    List<YzjSysAuthority> selectByPrimary(String page, String limit);
}
