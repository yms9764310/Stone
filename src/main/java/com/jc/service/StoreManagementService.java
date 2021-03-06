package com.jc.service;

import com.jc.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.InputStream;
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
    //统计损耗量
    List<Store>  countStoreLoss();

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

    //导入结果单
    String importExcel(InputStream inputStream, String fileName)throws Exception;

    //修改盘点任务
    String SureCountingTask(StoreCheck storeCheck);

    //审核盘点结果单
    String ReviewCountingTask(StoreCheck storeCheck);

    //驳回盘点结果单
    String updateCheckState(StoreCheck storeCheck);

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

    String exportExcel(String[] titles, ServletOutputStream outputStream, Integer check_id);
}
