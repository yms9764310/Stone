package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;

import com.jc.model.YzjSysLoginUser;
import com.jc.model.YzjSysPurchaseProduct;
import com.jc.model.YzjSysResource;
import com.jc.service.YzjSysLoginUserTestService;
import com.jc.service.YzjSysPurchaseProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/prou")

public class YzjSysPurchaseProductController {
    @Resource
    private YzjSysPurchaseProductService yzjSysPurchaseProductServiceImpl;
    @Resource
    private YzjSysLoginUserTestService sysLoginUserTestServiceImpl;
    //分页查询
    @RequestMapping("/listprou")
    @ResponseBody
    @RequiresPermissions("list:selec")
//    @RequiresPermissions("list:listprou")
    public IResult getPurchaseProduct(String page, String limit){
        List<YzjSysPurchaseProduct> resultData = yzjSysPurchaseProductServiceImpl.listYzjSysPurchaseProduct(page,limit);
        return new PageResultBean<Collection<YzjSysPurchaseProduct>>(resultData,yzjSysPurchaseProductServiceImpl.countYzjSysPurchaseProduct());
    }
    //删除
    @RequestMapping("/deleteprou")
    @ResponseBody
    public IResult deletePurchaseProduct(int id){
        //接收前端过来的id，根据其id，再数据库中查询，并进行删除
        return new ResultBean<Integer>(yzjSysPurchaseProductServiceImpl.deleteYzjSysPurchaseProduct(id));
    }
    //根据id查询
    @RequestMapping("/loadprou")
    @ResponseBody
    public IResult loadPurchaseProduct(int id){
        //获取编辑页面的值
        return new ResultBean<YzjSysPurchaseProduct>(yzjSysPurchaseProductServiceImpl.loadYzjSysPurchaseProductById(id));
    }
    //添加单个商品信息
    @RequestMapping("/saveprou")
    @ResponseBody
    public IResult savePurchaseProduct(@RequestBody YzjSysPurchaseProduct yzjSysPurchaseProduct) {

        return new ResultBean<Integer>(yzjSysPurchaseProductServiceImpl.saveYzjSysPurchaseProduct(yzjSysPurchaseProduct));
    }
    //编辑商品
    @RequestMapping("/updateprou")
    @ResponseBody
    public IResult updateprou(@RequestBody YzjSysPurchaseProduct yzjSysPurchaseProduct) {
        return new ResultBean<Integer>(yzjSysPurchaseProductServiceImpl.updateYzjSysPurchaseProduct(yzjSysPurchaseProduct));
    }
    //批量导入
    @ResponseBody
    @RequestMapping(value="/fileUploadprou")
    public IResult UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ResultBean<String>(yzjSysPurchaseProductServiceImpl.ajaxUploadExcel(request,response));
    }

}
