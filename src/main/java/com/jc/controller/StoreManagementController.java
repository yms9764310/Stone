package com.jc.controller;


import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.socket.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        return new PageResultBean<Collection<StoreCheck>>(resultData,storeManagementServiceImpl.countGetAll());
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
    public IResult sureCountingTask(@RequestBody StoreCheck storeCheck) {
        //返回json至前端的均返回ResultBean或者PageResultBea
        String s = storeManagementServiceImpl.SureCountingTask(storeCheck);
        return new ResultBean<String>(s);
    }

    /**
     * 审核盘点结果单
     */
    @RequestMapping("/reviewCountingTask")
    @ResponseBody
    public IResult reviewCountingTask(@RequestBody StoreCheck storeCheck) {
        //返回json至前端的均返回ResultBean或者PageResultBea
        String s = storeManagementServiceImpl.ReviewCountingTask(storeCheck);
        return new ResultBean<String>(s);
    }

    /**
     * 驳回盘点结果单
     */
    @RequestMapping("/updateCheckState")
    @ResponseBody
    public IResult updateCheckState(@RequestBody StoreCheck storeCheck) {
        //返回json至前端的均返回ResultBean或者PageResultBea
        String s = storeManagementServiceImpl.updateCheckState(storeCheck);
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
    /**
     * 导入结果单
     * */
    @ResponseBody
    @RequestMapping(value="/fileUpload")
    public IResult uploadExcel(MultipartFile upfile) throws Exception {
        // file.getOriginalFilename();   获取文件名
        return new ResultBean<String>(storeManagementServiceImpl.importExcel(upfile.getInputStream(), upfile.getOriginalFilename()));
    }

    //导出盘点任务单
    @RequestMapping("/exportExcel")
    @ResponseBody
    public String exportExcel(HttpServletResponse response,String check_id) {
        response.setContentType("application/binary;charset=utf-8");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = new String(("导出excel例子").getBytes(), "utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "盘点人", "开始时间", "结束时间" , "商品名称","库存数量","盘点数量"};
            storeManagementServiceImpl.exportExcel(titles, outputStream,Integer.valueOf(check_id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    /**
     * 统计库存损耗
     */
    @RequestMapping("/countStoreLoss")
    @ResponseBody
    public IResult countStoreLoss() {
        //返回json至前端的均返回ResultBean或者PageResultBea
        List<Store> stores = storeManagementServiceImpl.countStoreLoss();
        return new ResultBean<Collection<Store>>(stores);
    }
}
