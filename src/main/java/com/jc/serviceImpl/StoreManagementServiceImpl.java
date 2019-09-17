package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StoreManagementMapper;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.service.StorePutInService;
import com.jc.utils.ExportDataToExcle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class StoreManagementServiceImpl implements StoreManagementService {
    @Resource
    private StoreManagementMapper storeManagementMapper;

    Lock l = new ReentrantLock();//创建锁对象

    @Override
    public List<Store> listAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                List<Store> stores = storeManagementMapper.listAll(pageRange.getStart(), pageRange.getEnd(), name);
                return stores;
            }
        }
        return null;
    }

    @Override
    public List<StoreCheck> listCheckAll(String page, String limit, String startTime, String endTime) {
        PageRange pageRange = new PageRange(page, limit);
        //判断是否是仓库管理人员
        //先获取当前账号的ID,判断是否是主管
        int id = 4;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                List<StoreCheck> storeChecks = storeManagementMapper.listCheckAll(pageRange.getStart(), pageRange.getEnd(), startTime, endTime);
                return storeChecks;
            } else {
                String name = sysUsersBeans.getUser_name();
                List<StoreCheck> storeChecks = storeManagementMapper.listCheckWith(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name);
                return storeChecks;
            }
        }
        return null;
    }

    @Override
    public int countGetAll() {
        return storeManagementMapper.countGetAll();
    }

    @Override
    public int countGetCheckAll() {
        return storeManagementMapper.countGetCheckAll();
    }

    @Override
    public int countGetCheckOutAll() {
        return storeManagementMapper.countGetCheckOutAll();
    }

    @Override
    public int countGetPutInAll() {
        return storeManagementMapper.countGetPutInAll();
    }

    public int deleteCheckTask(Integer id) {
        List<StoreCheckTaskDetail> storeCheckTaskDetails = storeManagementMapper.loadByCheckId(id);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetails) {
            storeManagementMapper.deleteCheckTaskDetail(storeCheckTaskDetail.getCheck_id());
        }
        storeManagementMapper.deleteCheckTask(id);
        return id;
    }

    @Override
    public String insertCountingTask(StoreCheck storeCheck) {
        Date date = new Date();//获取时间
        StoreCheck storeCheck1 = new StoreCheck("3", date,
                storeCheck.getCheck_user_id(), "3", storeCheck.getBegin_date(), storeCheck.getEnd_date());
        storeManagementMapper.insertCheckTask(storeCheck1);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheck.getStoreCheckTaskDetailList()) {
            storeCheckTaskDetail.setCheck_id(storeCheck1.getId());
            storeManagementMapper.insertCheckTaskDetail(storeCheckTaskDetail);
        }

        return "success";
    }

    @Override
    public Store loadByProductId(String product_id) {
        return storeManagementMapper.loadByProductId(product_id);
    }

    @Override
    public SysUsersBeans loadById(Integer id) {
        return storeManagementMapper.loadById(id);
    }

    @Override
    public StoreCheck loadByCheck_Id(Integer id) {
        StoreCheck storeCheck = storeManagementMapper.loadByCheck_Id(id);
        List<StoreCheckTaskDetail> storeCheckTaskDetails = storeManagementMapper.loadByCheckId(id);
        storeCheck.setStoreCheckTaskDetailList(storeCheckTaskDetails);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetails) {
            int product_id =storeCheckTaskDetail.getProduct_id();
            int number = storeManagementMapper.count(product_id);
            storeCheckTaskDetail.setNumber(number);
            storeCheck.setStoreCheckTaskDetailList(storeCheckTaskDetails);
        }
        return storeCheck;
    }

    @Override
    public String updateCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                Date date = new Date();//获取时间
                StoreCheck storeCheck1 = new StoreCheck("3", date, storeCheck.getCheck_user_id(),
                        storeCheck.getBegin_date(), "5", storeCheck.getEnd_date(), storeCheck.getId());
                storeManagementMapper.updateCountingTask(storeCheck1);
                storeManagementMapper.deleteCheckTaskDetail(storeCheck1.getId());
                List<StoreCheckTaskDetail> storeCheckTaskDetail = storeCheck.getStoreCheckTaskDetailList();
                for (StoreCheckTaskDetail checkTaskDetail : storeCheckTaskDetail) {
                    StoreCheckTaskDetail storeCheckTaskDetail1 = new StoreCheckTaskDetail();
                    storeCheckTaskDetail1.setCheck_id(storeCheck1.getId());
                    storeCheckTaskDetail1.setProduct_id(checkTaskDetail.getValue());
                    storeManagementMapper.updateCountingTaskDetail(storeCheckTaskDetail1);
                }
                return "success";
            }
        }
        return "error";
    }

    @Override
    public String SureCountingTask(StoreCheck StoreCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = StoreCheck.getStoreCheckTaskDetailList();
            for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetailList) {
                storeManagementMapper.SureCountingTask(storeCheckTaskDetail);
            }
            return "success";
        }
        return "error";
    }


    public StoreWarn loadByProduct_id(Integer product_id) {
        StoreWarn storeWarn = storeManagementMapper.loadByProduct_id(product_id);
        return storeWarn;
    }

    @Override
    public String updateWarn(StoreWarn storeWarn) {
        l.lock();//加锁
        try {
            //先获取当前账号的ID,判断是否是主管
            int id = 3;
            SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                    storeManagementMapper.updateWarn(storeWarn);
                    return "success";
                }
            }
        } finally {
            l.unlock(); // 解锁
        }
        return "error";

    }

    public List<SysUsers> listUsers() {
        //根据主管账号查询当前部门下的人员
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                //获取本部门人员
                String department_id = sysUsersBeans.getDepartment_id();
                return storeManagementMapper.listUsers(department_id);
            }
        }
        return null;
    }

    @Override
    public List<StoreCheckOut> listCheckOut(String page, String limit, String startTime, String endTime, String name, String source_kind) {
        PageRange pageRange = new PageRange(page, limit);
        return storeManagementMapper.listCheckOut(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name, source_kind);
    }

    @Override
    public List<StorePutIn> listPutIn(String page, String limit, String startTime, String endTime, String name, String source_type) {
        PageRange pageRange = new PageRange(page, limit);
        return storeManagementMapper.listPutIn(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name, source_type);
    }


    public HSSFWorkbook exportExcelInfo(Integer check_id) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        List<StoreCheckTaskDetail> list = storeManagementMapper.loadByCheckId(check_id);
        HSSFWorkbook xssfWorkbook = null;
        //调用ExcelUtil的方法
        try {
            xssfWorkbook = ExportDataToExcle.export(list, "E:\\Temp\\盘点任务单.xls", "com.jc.model.StoreCheckTaskDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }

    public List<ToDoList> listLimitData(String page, String limit, List<ToDoList> limitData) {
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        //起始下标
        int fromIndex = Integer.valueOf(limit) * (Integer.valueOf(page) - 1);
        //终止下标
        int intLimit = Integer.valueOf(limit);
        int toIndex = fromIndex + intLimit;
        int size = limitData.size();
        if (toIndex >= size) {
            toIndex = size;
        }
        resultData = limitData.subList(fromIndex, toIndex);
        return resultData;
    }
}
