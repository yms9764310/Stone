package com.jc.mapper;

import com.jc.model.SysLoginUser;
import com.jc.model.SysUsers;
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

}
