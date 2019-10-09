package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.*;
import com.jc.service.StorePutInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
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
@RequestMapping("/StorePutIn")
public class StorePutInController {
    @Resource
    StorePutInService storePutInServiceImpl;
    /**
     * 出/入库查看
     * */
    @RequestMapping("/StorePut")
    @ResponseBody
    public IResult listAll(String page, String limit, String name,String startTime,String endTime,
                           String typename){
        //返回json至前端的均返回ResultBean或者PageResultBean
        List<ToDoList> resultData = storePutInServiceImpl.listAll(page, limit,name,startTime,endTime,typename);
        return new PageResultBean<Collection<ToDoList>>(resultData,resultData.size());
    }

    /**
     * 入库查看
     * */
    @RequestMapping("/getPutIn")
    @ResponseBody
    public IResult loadPutIn(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        StorePutIn storePutIn = storePutInServiceImpl.loadByPutInId(id);
        return new ResultBean<StorePutIn>(storePutIn);
    }

    /**
     * 出库查看
     * */
    @RequestMapping("/getCheckOut")
    @ResponseBody
    public IResult loadOut(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        StoreCheckOut storeCheckOut = storePutInServiceImpl.loadByCheckOutId(id);
        return new ResultBean<StoreCheckOut>(storeCheckOut);
    }

    /**
     * 入库通过
     */
    @RequestMapping("/updatePutInSuccess")
    @ResponseBody
    public IResult reviewPutInSuccess(@RequestBody StorePutIn storePutIn) throws SQLException {
        return new ResultBean<String>(storePutInServiceImpl.updatePutInSuccess(storePutIn));
    }

    /**
     * 入库驳回
     */
    @RequestMapping("/updatePutInReject")
    @ResponseBody
    public IResult updatePutInReject(@RequestBody StorePutIn storePutIn){
        return new ResultBean<String>(storePutInServiceImpl.updatePutInReject(storePutIn));
    }

    /**
     * 出库通过
     */
    @RequestMapping("/updateCheckOutSuccess")
    @ResponseBody
    public IResult reviewCheckOutSuccess(@RequestBody StoreCheckOut storeCheckOut){
        Consants.PRODUCTID=storeCheckOut.getProduct_id();
        return new ResultBean<String>(storePutInServiceImpl.updateCheckOutSuccess(storeCheckOut));
    }

    /**
     * 出库驳回
     */
    @RequestMapping("/updateCheckOutReject")
    @ResponseBody
    public IResult updateCheckOutReject(@RequestBody StoreCheckOut storeCheckOut){
        return new ResultBean<String>(storePutInServiceImpl.updateCheckOutReject(storeCheckOut));
    }

    /**
     * 添加出库单
     */
    @RequestMapping("/insertCheckOut")
    @ResponseBody
    public IResult insertCheckOut(@RequestBody StoreCheckOut storeCheckOut){
        return new ResultBean<String>(storePutInServiceImpl.insertCheckOut(storeCheckOut));
    }

    /**
     * 添加入库单
     */
    @RequestMapping("/insertStorePutIn")
    @ResponseBody
    public IResult insertStorePutIn(@RequestBody StorePutIn storePutIn){
        return new ResultBean<String>(storePutInServiceImpl.insertStorePutIn(storePutIn));
    }

}
