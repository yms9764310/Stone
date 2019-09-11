package com.jc.service;

import com.jc.model.*;

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

public interface StoreManagementService {

    List<Store> listAll(String page, String limit,String name);

    List<StoreCheck> listCheckAll(String page, String limit, String startTime, String endTime);

    int countGetAll();

    int countGetCheckAll();

    int countGetCheckOutAll();

    int countGetPutInAll();

    //根据商品ID查询
    Store loadByProductId(String product_id);

    SysUsersBeans loadById(Integer id);

    //根据商品ID查询
    StoreWarn loadByProduct_id(Integer product_id);

    //设置库存预警
    String updateWarn(StoreWarn storeWarn);

    //查询全部人员
    List<SysUsers> listUsers();

    //查询出库记录
    List<StoreCheckOut> listCheckOut(String page, String limit, String startTime, String endTime,String name);
    //查询入库记录
    List<StorePutIn> listPutIn(String page, String limit, String startTime, String endTime,String name);
}
