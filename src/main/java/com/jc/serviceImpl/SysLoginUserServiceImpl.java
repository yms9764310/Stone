package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StudentMapper;
import com.jc.mapper.SysLoginUserMapper;
import com.jc.model.Student;
import com.jc.model.SysLoginUser;
import com.jc.service.StudentService;
import com.jc.service.SysLoginUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class SysLoginUserServiceImpl implements SysLoginUserService {
    @Resource
    private SysLoginUserMapper sysLoginUserMapper;

    public String updatePassword(SysLoginUser sysLoginUser) {
        sysLoginUserMapper.updatePassword(sysLoginUser);
        return "success";
    }

    public SysLoginUser loadById(Integer id) {
        return sysLoginUserMapper.loadById(id);
    }
}
