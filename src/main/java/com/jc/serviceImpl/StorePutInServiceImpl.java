package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.PurchaseBillMapper;
import com.jc.mapper.StoreManagementMapper;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.*;
import com.jc.service.StorePutInService;
import com.jc.socket.SocketHandler;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    // 注入webSocket的处理类
    @Autowired
    private SocketHandler socketHandler;
    @Resource
    private StorePutInMapper storePutInMapper;
    @Resource
    private PurchaseBillMapper purchaseBillMapper;
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


    //入库审核
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
                    PurchaseBill purchaseBill=new PurchaseBill();
                    //查询应付单是否通过审核
                    AccountHandleBill accountHandle = purchaseBillMapper.loadAccountHandleBillAudit(Integer.parseInt(storePutIn.getSource_id()));
                    //查询入库单的相关商品是否都通过审核
                    List<StorePutIn> list = purchaseBillMapper.listStorePutInAudit(Integer.parseInt(storePutIn.getSource_id()));
                    int i=0;
                    for (StorePutIn putIn : list) {
                        if (putIn.getState().equals(6+"")){
                            i+=1;
                        }
                    }
                    if (accountHandle.getState().equals(6+"")&&i==list.size()){
                        purchaseBill.setId(Integer.parseInt(storePutIn.getSource_id()));
                        purchaseBill.setState(6+"");
                        purchaseBillMapper.updateBillAudit(purchaseBill);
                    }else{
                        purchaseBill.setState(5+"");
                        purchaseBill.setId(Integer.parseInt(storePutIn.getSource_id()));
                        purchaseBillMapper.updateBillAudit(purchaseBill);
                    }
                    socketHandler.sendForOne("审核结果通知","你的请求已通过审核",storePutIn.getPut_in_user_id());
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
                socketHandler.sendForOne("驳回通知","你的请求被驳回",storePutIn.getPut_in_user_id());
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
                        socketHandler.sendForOne("审核结果通知","你的请求已通过审核",String.valueOf(storeCheckOut.getCheckout_user_id()));
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
                socketHandler.sendForOne("驳回通知","你的请求被驳回",String.valueOf(storeCheckOut.getCheckout_user_id()));
                return "success";
            }
        }
        return "error";
    }

    @Override
    public String insertCheckOut(StoreCheckOut storeCheckOut) {
        storePutInMapper.insertCheckOut(storeCheckOut);
       // socketHandler.sendForOne("待审核通知","有工作待审核",String.valueOf(storeCheckOut.getCheckout_user_id()));
        return "success";
    }

    @Override
    public String insertStorePutIn(StorePutIn storePutIn) {
        storePutInMapper.insertStorePutIn(storePutIn);
        //socketHandler.sendForOne("待审核通知","有工作待审核",storePutIn.getPut_in_user_id());
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
