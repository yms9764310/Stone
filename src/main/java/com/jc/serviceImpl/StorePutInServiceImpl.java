package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StoreManagementMapper;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.*;
import com.jc.service.StorePutInService;
import org.apache.shiro.SecurityUtils;
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
public class StorePutInServiceImpl implements StorePutInService {
    @Resource
    private StorePutInMapper storePutInMapper;
    @Resource
    private StoreManagementMapper storeManagementMapper;

    Lock l = new ReentrantLock();//创建锁对象

    public List<ToDoList> listAll(String page, String limit, String name, String startTime, String endTime, String typename) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        SysUsersBeans sysUsersBeans = storePutInMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                if ("入库单".equals(typename)) {
                    List<ToDoList> listStorePutIn = storePutInMapper.listStorePutIn(pageRange.getStart(), pageRange.getEnd(), name, startTime, endTime, typename);
                    for (ToDoList toDoList : listStorePutIn) {
                        toDoList.setTypename("入库单");
                        resultData.add(toDoList);
                    }
                    return listLimitData(page, limit, resultData);
                } else if ("出库单".equals(typename)) {
                    List<ToDoList> listStoreCheckout = storePutInMapper.listStoreCheckout(pageRange.getStart(), pageRange.getEnd(), name, startTime, endTime, typename);
                    for (ToDoList toDoList : listStoreCheckout) {
                        toDoList.setSource_type("销售单");
                        toDoList.setTypename("出库单");
                        resultData.add(toDoList);
                    }
                    return listLimitData(page, limit, resultData);
                } else {
                    List<ToDoList> listStorePutIn = storePutInMapper.listStorePutIn(pageRange.getStart(), pageRange.getEnd(), name, startTime, endTime, typename);
                    for (ToDoList toDoList : listStorePutIn) {
                        toDoList.setTypename("入库单");
                        resultData.add(toDoList);
                    }
                    List<ToDoList> listStoreCheckout = storePutInMapper.listStoreCheckout(pageRange.getStart(), pageRange.getEnd(), name, startTime, endTime, typename);
                    for (ToDoList toDoList : listStoreCheckout) {
                        toDoList.setSource_type("销售单");
                        toDoList.setTypename("出库单");
                        resultData.add(toDoList);
                    }
                }
                return listLimitData(page, limit, resultData);
            }
        }
        return null;
    }

    public int countGetAll() {
        return storePutInMapper.countGetAll();
    }

    @Override
    public SysUsersBeans loadById(Integer id) {
        return storePutInMapper.loadById(id);
    }

    @Override
    public StorePutIn loadByPutInId(Integer id) {
        return storePutInMapper.loadByPutInId(id);
    }

    @Override
    public StoreCheckOut loadByCheckOutId(Integer id) {
        return storePutInMapper.loadByCheckOutId(id);
    }


    @Override
    public String updatePutInSuccess(StorePutIn storePutIn) {
        l.lock();//加锁
        try {
            //先获取当前账号的ID,判断是否是主管
            SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
            int id = user.getId();
            SysUsersBeans sysUsersBeans = storePutInMapper.loadById(id);
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                    //调用存储过程
                    storePutInMapper.updateStore(storePutIn); //修改
                    storePutInMapper.updatePutInSuccess(storePutIn);//修改状态
                    return "success";
                }
            }
        } finally {
            l.unlock(); // 解锁
        }
        return "error";
    }

    @Override
    public String updatePutInReject(StorePutIn storePutIn) {
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysUsersBeans sysUsersBeans = storePutInMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                storePutInMapper.updatePutInReject(storePutIn);
                return "success";
            }
        }
        return "error";
    }


    @Override
    public String updateCheckOutSuccess(StoreCheckOut storeCheckOut) {

        l.lock();//加锁
        try {
            //先获取当前账号的ID,判断是否是主管
            SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
            int id = user.getId();
            SysUsersBeans sysUsersBeans = storePutInMapper.loadById(id);
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                    Store store = storePutInMapper.loadByProductId(storeCheckOut.getProduct_id());
                    if (storeCheckOut.getNumber() <= store.getNumber()) {
                        //调用存储过程
                        storePutInMapper.updateStoreCheck(storeCheckOut);
                        storePutInMapper.updateCheckOutSuccess(storeCheckOut);//修改状态
                        return "success";
                    } else {
                        return "break";
                    }
                }
            }
        } finally {
            l.unlock(); // 解锁
        }
        return "error";


    }

    @Override
    public String updateCheckOutReject(StoreCheckOut storeCheckOut) {
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysUsersBeans sysUsersBeans = storePutInMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                storePutInMapper.updateCheckOutReject(storeCheckOut);
                return "success";
            }
        }
        return "error";
    }

    @Override
    public String insertCheckOut(StoreCheckOut storeCheckOut) {
        storePutInMapper.insertCheckOut(storeCheckOut);
        return "success";
    }

    @Override
    public String insertStorePutIn(StorePutIn storePutIn) {
        storePutInMapper.insertStorePutIn(storePutIn);
        return "success";
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
