package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.mapper.AssignmentWorkMapper;
import com.jc.mapper.StaffSettingMapper;
import com.jc.model.SysUsers;
import com.jc.model.SysUsersBeans;
import com.jc.model.SysUsersExceptSale;
import com.jc.service.AssignmentWorkService;
import com.jc.service.StaffSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Service
@Transactional
public class AssignmentWorkServiceImpl implements AssignmentWorkService {
    @Resource
    private AssignmentWorkMapper assignmentWorkMapper;

    public String updateMessage(SysUsers sysUsers) {
        assignmentWorkMapper.updateMessage(sysUsers);
        return "success";
    }

    public SysUsersBeans loadById(Integer id) {

        return assignmentWorkMapper.loadById(id);
    }

    public List<SysUsers> ListSaleAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return assignmentWorkMapper.ListSaleAll(pageRange.getStart(),pageRange.getEnd(),name);
    }

    @Override
    public List<SysUsersExceptSale> ListAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return assignmentWorkMapper.ListAll(pageRange.getStart(),pageRange.getEnd(),name);
    }

    public int countGetAll() {
        return assignmentWorkMapper.countGetAll();
    }
}
