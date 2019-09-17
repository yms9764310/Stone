package com.jc.service;

import com.jc.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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

    List<Store> listStoreAll(String page, String limit,String name);

    StoreCheckOut loadStoreCheckOut(Integer product_id);

    List<StoreCheck> listCheckAll(String page, String limit, String startTime, String endTime);

    int countGetAll();

    int countGetCheckAll();

    int countGetCheckOutAll();

    int countGetPutInAll();

    int deleteCheckTask(Integer id);

    String insertCountingTask(StoreCheck storeCheck);

    //根据商品ID查询
    Store loadByProductId(String product_id);

    SysUsersBeans loadById(Integer id);

    //根据盘点任务ID查询盘点任务
    StoreCheck loadByCheck_Id(Integer id);

    //修改盘点任务
    String updateCountingTask(StoreCheck storeCheck);

    //修改盘点任务
    String SureCountingTask(StoreCheck storeCheck);

    //根据商品ID查询
    StoreWarn loadByProduct_id(Integer product_id);

    //设置库存预警
    String updateWarn(StoreWarn storeWarn);

    //查询全部人员
    List<SysUsers> listUsers();

    //查询出库记录
    List<StoreCheckOut> listCheckOut(String page, String limit, String startTime, String endTime,String name,String source_kind);
    //查询入库记录
    List<StorePutIn> listPutIn(String page, String limit, String startTime, String endTime,String name,String source_type);

    HSSFWorkbook exportExcelInfo(Integer check_id) throws InvocationTargetException,
            ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException;
}
