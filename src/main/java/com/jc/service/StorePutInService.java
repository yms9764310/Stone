package com.jc.service;

import com.jc.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 41
 *
 * @author 严脱兔
 */

public interface StorePutInService {

    List<ToDoList> listAll(String page, String limit, String put_in_user_id,
                           String startTime, String endTime, String typename);
    int countGetAll();

    //根据ID查询
    SysUsersBeans loadById(Integer id);
    //根据入库ID查询
    StorePutIn loadByPutInId(Integer id);
    //根据出库ID查询
    StoreCheckOut loadByCheckOutId(Integer id);
    //入库审核通过
    String updatePutInSuccess(StorePutIn storePutIn);
    //入库驳回
    String updatePutInReject(StorePutIn storePutIn);
    //出库审核通过
    String updateCheckOutSuccess(StoreCheckOut storeCheckOut);
    //出库驳回
    String updateCheckOutReject(StoreCheckOut storeCheckOut);
    //添加出库单
    String insertCheckOut(StoreCheckOut storeCheckOut);
    //添加入库单
    String insertStorePutIn(StorePutIn storePutIn);


}
