package com.jc.controller;


import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 年: 2019
 * 月: 08
 * 日: 29
 * 小时: 14
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Slf4j
@Controller
@RequestMapping("/StoreManagement")
public class StoreManagementController {
    @Resource
    StoreManagementService storeManagementServiceImpl;

    /**
     * 查看全部库存
     */
    @RequestMapping("/StoreInventory")
    @ResponseBody
    public IResult listStoreAll(String page, String limit, String name) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<Store> resultData = storeManagementServiceImpl.listStoreAll(page, limit, name);
        return new PageResultBean<Collection<Store>>(resultData, storeManagementServiceImpl.countGetAll());
    }

    /**
     * 查看活动量/锁定量
     */
    @RequestMapping("/StoreNumber")
    @ResponseBody
    public IResult loadStoreCheckOut(Integer product_id) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        StoreCheckOut storeCheckOut = storeManagementServiceImpl.loadStoreCheckOut(product_id);
        return new ResultBean<StoreCheckOut>(storeCheckOut);
    }

    /**
     * 查看盘点任务
     */
    @RequestMapping("/StoreCheck")
    @ResponseBody
    public IResult listCheckAll(String page, String limit, String startTime, String endTime) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StoreCheck> resultData = storeManagementServiceImpl.listCheckAll(page, limit, startTime, endTime);
        return new PageResultBean<Collection<StoreCheck>>(resultData,resultData.size());
    }


    /**
     * 出库预警
     */
    @RequestMapping("/getCheckOut")
    @ResponseBody
    public IResult loadProductId(Integer product_id) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        StoreWarn storeWarn = storeManagementServiceImpl.loadByProduct_id(product_id);
        return new ResultBean<StoreWarn>(storeWarn);
    }

    /**
     * 设置预警值
     */
    @RequestMapping("/updateCheckOutWarn")
    @ResponseBody
    public IResult updateWarn(@RequestBody StoreWarn storeWarn) {
        String result = storeManagementServiceImpl.updateWarn(storeWarn);
        return new ResultBean<String>(result);
    }

    /**
     * 查询人员
     */
    @RequestMapping("/findUsersName")
    @ResponseBody
    public IResult listUsers() {
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<SysUsers>>(storeManagementServiceImpl.listUsers());
    }

    /**
     * 查询出库
     */
    @RequestMapping("/findCheckOut")
    @ResponseBody
    public IResult listCheckOut(String page, String limit, String startTime, String endTime, String name, String source_kind) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StoreCheckOut> storeCheckOuts = storeManagementServiceImpl.listCheckOut(page, limit, startTime, endTime, name, source_kind);
        return new PageResultBean<Collection<StoreCheckOut>>(storeCheckOuts, storeManagementServiceImpl.countGetCheckOutAll());
    }

    /**
     * 查询入库
     */
    @RequestMapping("/findPutIn")
    @ResponseBody
    public IResult listPutIn(String page, String limit, String startTime, String endTime, String name, String source_type) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StorePutIn> storePutIns = storeManagementServiceImpl.listPutIn(page, limit, startTime, endTime, name, source_type);
        return new PageResultBean<Collection<StorePutIn>>(storePutIns, storeManagementServiceImpl.countGetPutInAll());
    }

    /**
     * 添加盘点任务
     */
    @RequestMapping("/insertCountingTask")
    @ResponseBody
    public IResult insertCounting(@RequestBody StoreCheck storeCheck) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        String s = storeManagementServiceImpl.insertCountingTask(storeCheck);
        return new ResultBean<String>(s);
    }

    /**
     * 删除盘点任务
     */
    @RequestMapping("/deleteCheckTask")
    @ResponseBody
    public IResult deleteCheck(Integer id) {
        //返回json至前端的均返回ResultBean或者PageResultBean
        storeManagementServiceImpl.deleteCheckTask(id);
        return new ResultBean<Integer>(id);
    }

    /**
     * 获取盘点任务
     */
    @RequestMapping("/getCheckTask")
    @ResponseBody
    public IResult getMenu(Integer id) {
        //返回json至前端的均返回ResultBean或者PageResultBea
        StoreCheck storeCheck = storeManagementServiceImpl.loadByCheck_Id(id);
        return new ResultBean<StoreCheck>(storeCheck);
    }

    /**
     * 盘点确定
     */
    @RequestMapping("/sureCountingTask")
    @ResponseBody
    public IResult SureCountingTask(@RequestBody StoreCheck storeCheck) {
        //返回json至前端的均返回ResultBean或者PageResultBea
        String s = storeManagementServiceImpl.SureCountingTask(storeCheck);
        return new ResultBean<String>(s);
    }

    /**
     * 确定盘点任务
     */
    @RequestMapping("/updateCountingTask")
    @ResponseBody
    public IResult updateMenu(@RequestBody StoreCheck storeCheck) {//传递了数组，前台放在payload里面了，后台通过@RequestBody获取
        //返回json至前端的均返回ResultBean或者PageResultBean

        return new ResultBean<String>(storeManagementServiceImpl.updateCountingTask(storeCheck));
    }




    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    @ResponseBody
    public void export(Integer check_id, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        if (check_id != null) {
            response.reset(); //清除buffer缓存
            Map<String, Object> map = new HashMap<String, Object>();
            System.out.println("文件名：");
            // 指定下载的文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + check_id + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            HSSFWorkbook workbook = null;
            //导出Excel对象
            try {
                workbook = storeManagementServiceImpl.exportExcelInfo(check_id);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            OutputStream output;
            try {
                output = response.getOutputStream();
                BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
                bufferedOutPut.flush();
                workbook.write(bufferedOutPut);
                bufferedOutPut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
