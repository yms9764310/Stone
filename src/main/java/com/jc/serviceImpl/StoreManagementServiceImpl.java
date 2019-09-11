package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StoreManagementMapper;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.service.StorePutInService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
    public List<Store> listAll(String page, String limit,String name) {
        PageRange pageRange = new PageRange(page, limit);
        return storeManagementMapper.listAll(pageRange.getStart(), pageRange.getEnd(),name);
    }

    @Override
    public List<StoreCheck> listCheckAll(String page, String limit, String startTime, String endTime) {
        PageRange pageRange = new PageRange(page, limit);
        List<StoreCheck> storeChecks = storeManagementMapper.listCheckAll(pageRange.getStart(), pageRange.getEnd(), startTime, endTime);
        return storeChecks;
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

    @Override
    public Store loadByProductId(String product_id) {
        return storeManagementMapper.loadByProductId(product_id);
    }

    @Override
    public SysUsersBeans loadById(Integer id) {
        return storeManagementMapper.loadById(id);
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
            if (sysUsersBeans.getName().equals("主管") && sysUsersBeans.getRole_id().equals("1")) {
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
        return storeManagementMapper.listUsers();
    }

    @Override
    public List<StoreCheckOut> listCheckOut(String page, String limit, String startTime, String endTime,String name) {
        PageRange pageRange = new PageRange(page, limit);
        return storeManagementMapper.listCheckOut(pageRange.getStart(), pageRange.getEnd(), startTime, endTime,name);
    }

    @Override
    public List<StorePutIn> listPutIn(String page, String limit, String startTime, String endTime,String name) {
        PageRange pageRange = new PageRange(page, limit);
        return storeManagementMapper.listPutIn(pageRange.getStart(), pageRange.getEnd(), startTime, endTime,name);
    }
}
