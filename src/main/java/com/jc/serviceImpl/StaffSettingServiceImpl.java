package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.mapper.StaffSettingMapper;
import com.jc.mapper.SysUsersMapper;
import com.jc.model.*;
import com.jc.service.StaffSettingService;
import com.jc.service.SysUsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 14
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class StaffSettingServiceImpl implements StaffSettingService {
    @Resource
    private StaffSettingMapper staffSettingMapper;

    public int updateMessage(SysUsers sysUsers) {
        //先获取当前账号的ID,判断是否是主管
        int id = 1;
        SysUsersBeans sysUsersBeans =staffSettingMapper.loadById(id);
        if(sysUsersBeans.getName().equals("主管")&&sysUsersBeans.getRole_id().equals("1")){
            //判断是否是销售部
            if(sysUsersBeans.getDepart_id().equals("销售部")){
                // 修改的时候获取当前时间
                Date date = new Date();
                // 修改的时候获取当前账号
                SysUsers sysUsers1 = new SysUsers(sysUsers.getId(),"1",date,sysUsers.getName(),
                        sysUsers.getSex(),sysUsers.getAge(),sysUsers.getPhone(),sysUsers.getJob_id(),
                        sysUsers.getDepart_id(),sysUsers.getDepart_role_id());
                SysUsers sysUsers2 = new SysUsers("1",sysUsers.getModify_date(),sysUsers.getMax_threshold());
                //调用方法
                staffSettingMapper.updateMessage(sysUsers1);
                staffSettingMapper.updateThreshold(sysUsers2);
                int code = 300;
                return code;
            }else {
                Date date = new Date();
               // 修改的时候获取当前账号
                SysUsers sysUsers1 = new SysUsers(sysUsers.getId(),"1",date,sysUsers.getName(),
                        sysUsers.getSex(),sysUsers.getAge(),sysUsers.getPhone(),sysUsers.getJob_id(),
                        sysUsers.getDepart_id(),sysUsers.getDepart_role_id());
                //调用方法
                staffSettingMapper.updateMessage(sysUsers1);
                int code = 300;
                return code;
            }

        }else {
            int code = 400;
            return code;
        }

    }

    @Override
    public int updateThreshold(SysUsers sysUsers) {
        //先获取当前账号的ID,判断是否是主管
        int id = 1;
        SysUsersBeans sysUsersBeans =staffSettingMapper.loadById(id);
        if(sysUsersBeans.getName().equals("主管")&&sysUsersBeans.getRole_id().equals("1")) {
            //判断是否是销售部
            if (sysUsersBeans.getDepart_id().equals("财务部")) {
                staffSettingMapper.updateThreshold(sysUsers);
                int code = 300;
                return code;
            }else {
                int code = 400;
                return code;
            }

        }else {
            int code = 400;
            return code;
        }

    }

    public SysUsersBeans loadById(Integer id) {

        return staffSettingMapper.loadById(id);
    }

    @Override
    public SysUsers loadByUserId(Integer id) {
        return staffSettingMapper.loadByUserId(id);
    }




    public int countGetAll() {
        return staffSettingMapper.countGetAll();
    }
    public List<SysUsers> loadUsers(String page, String limit,String name){
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        int id = 1;
        SysUsersBeans sysUsersBeans =staffSettingMapper.loadById(id);
        if(sysUsersBeans.getName().equals("主管")&&sysUsersBeans.getRole_id().equals("1")){
            //判断是否是销售部
            if(sysUsersBeans.getDepart_id().equals("财务部")){
                //获取本部门人员
              String department_id = sysUsersBeans.getDepartment_id();
                return staffSettingMapper.listSaleAll(pageRange.getStart(), pageRange.getEnd(),name,department_id);
            }else {
                String department_id = sysUsersBeans.getDepartment_id();
                return  staffSettingMapper.listAll(pageRange.getStart(), pageRange.getEnd(),name,department_id);
            }
        }
        String department_id = sysUsersBeans.getDepartment_id();
        return staffSettingMapper.listAllUsers(pageRange.getStart(), pageRange.getEnd(),name,department_id);
    }
}
