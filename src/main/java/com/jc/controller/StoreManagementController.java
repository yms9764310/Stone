package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.service.StorePutInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

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
     * */
    @RequestMapping("/StoreInventory")
    @ResponseBody
    public IResult listAll(String page, String limit,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<Store> resultData = storeManagementServiceImpl.listAll(page, limit,name);
        return new PageResultBean<Collection<Store>>(resultData,storeManagementServiceImpl.countGetAll());
    }

    /**
     * 查看盘点任务
     * */
    @RequestMapping("/StoreCheck")
    @ResponseBody
    public IResult listCheckAll(String page, String limit,String startTime,String endTime){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StoreCheck> resultData = storeManagementServiceImpl.listCheckAll(page, limit,startTime,endTime);
        return new PageResultBean<Collection<StoreCheck>>(resultData,storeManagementServiceImpl.countGetCheckAll());
    }


    /**
     * 出库预警
     * */
    @RequestMapping("/getCheckOut")
    @ResponseBody
    public IResult loadProductId(Integer product_id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        StoreWarn storeWarn = storeManagementServiceImpl.loadByProduct_id(product_id);
        return new ResultBean<StoreWarn>(storeWarn);
    }

    /**
     * 设置预警值
     */
    @RequestMapping("/updateCheckOutWarn")
    @ResponseBody
    public IResult updateWarn(@RequestBody StoreWarn storeWarn){
        String result = storeManagementServiceImpl.updateWarn(storeWarn);
        return new ResultBean<String>(result);
    }

    /**
     * 查询
     * */
    @RequestMapping("/findUsersName")
    @ResponseBody
    public IResult listUsers(){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Collection<SysUsers>>(storeManagementServiceImpl.listUsers());
    }

    /**
     * 查询出库
     * */
    @RequestMapping("/findCheckOut")
    @ResponseBody
    public IResult listCheckOut(String page, String limit,String startTime,String endTime,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StoreCheckOut> storeCheckOuts = storeManagementServiceImpl.listCheckOut(page, limit, startTime, endTime, name);
        return new PageResultBean<Collection<StoreCheckOut>>(storeCheckOuts,storeManagementServiceImpl.countGetCheckOutAll());
    }

    /**
     * 查询入库
     * */
    @RequestMapping("/findPutIn")
    @ResponseBody
    public IResult listPutIn(String page, String limit,String startTime,String endTime,String name){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<StorePutIn> storePutIns = storeManagementServiceImpl.listPutIn(page, limit, startTime, endTime, name);
        return new PageResultBean<Collection<StorePutIn>>(storePutIns,storeManagementServiceImpl.countGetPutInAll());
    }
}
