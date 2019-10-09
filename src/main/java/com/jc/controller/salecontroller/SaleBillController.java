package com.jc.controller.salecontroller;/*
 * @author 林锦杰
 * @date 2019/8/30 15:24
 * */

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysLoginUser;
import com.jc.model.TProductsyspurchaseproduct;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleBillDetail;
import com.jc.service.SysLoginUserService;
import com.jc.service.YzjSysLoginUserService;
import com.jc.service.sale.SaleBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/SaleBill")
public class SaleBillController {
    @Autowired
    SaleBillService saleBillServiceImpl;
    @Autowired
    SysLoginUserService sysLoginUserServiceImpl;
    /**
     * 查看所有订单
     */
    @RequestMapping("/listSaleBill")
    @ResponseBody
    public IResult listCustomer(String page, String limit, String creator, Integer bill_no){
        List<SaleBill> saleBills = saleBillServiceImpl.listSaleBill(page,limit,creator,bill_no);
        return new PageResultBean<Collection<SaleBill>>(saleBills,saleBillServiceImpl.countGetAll());
    }
    /**
     * 审核预览订单
     * */
    @RequestMapping("/loadById")
    @ResponseBody
    public IResult loadById1(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SaleBill>(saleBillServiceImpl.loadById1(id));
    }
    /**
     * 查看已审核订单
     * */
    @RequestMapping("/loadById2")
    @ResponseBody
    public IResult loadById2(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SaleBill>(saleBillServiceImpl.loadById2(id));
    }
    /**
     * 修改订单
     */
    @RequestMapping("/updateSaleBill")
    @ResponseBody
    public IResult updateSaleBill(@RequestBody SaleBill saleBill){
        String salebill = saleBillServiceImpl.updateSaleBill(saleBill);
        return new ResultBean<String>(salebill);
    }
    /*
     * 添加单个客户信息
     * */
    @RequestMapping(value = "/insertSaleBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertCustomer(@RequestBody SaleBill saleBill){
        String salebill = saleBillServiceImpl.insertSaleBill(saleBill);
        return new ResultBean<String>(salebill);
    }
    /**
     * 查看订单记录
     */
    @RequestMapping("/loadByCusId")
    @ResponseBody
    public IResult loadByCusId(Integer id){
        return new ResultBean<SaleBill>(saleBillServiceImpl.loadByCusId(id));
    }
    /*
    * 审核订单
    * */
    @RequestMapping(value = "/success",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateYes(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Integer>(saleBillServiceImpl.updateStateYes(id));
    }
    @RequestMapping(value = "/defeat",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateNo(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Integer>(saleBillServiceImpl.updateStateCancel(id));
    }
    /*
    * 查询所有商品
    * */
    @RequestMapping("/listProduct")
    @ResponseBody
    public IResult listProduct(String model_type){
        return new ResultBean<Collection<TProductsyspurchaseproduct>>(saleBillServiceImpl.loadByType(model_type));
    }
}
