package com.jc.controller.salecontroller;


import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleCustomer;
import com.jc.model.SysLoginUser;
import com.jc.service.sale.SaleBillService;
import com.jc.service.sale.SaleCustomerService;
import com.jc.service.SysLoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/Customer")
public class SaleCustomerController {
    @Autowired
    SaleCustomerService saleCustomerServiceImpl;
    @Autowired
    SysLoginUserService sysLoginUserServiceImpl;
    @Autowired
    SaleBillService saleBillServiceImpl;
    /**
     * 查看所有客户
     */
    @RequestMapping("/get")
    @ResponseBody
    public IResult listCustomer(String page, String limit, String name, String address){
        List<SaleCustomer> result = saleCustomerServiceImpl.listCustomer(page, limit, name, address);
        return new PageResultBean<Collection<SaleCustomer>>(result,saleCustomerServiceImpl.countGetAll());
    }
    /*
    * 添加单个客户信息
    * */
    @RequestMapping(value = "/insert.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertCustomer(@RequestBody SaleCustomer saleCustomer){
        int id = 2;
        SysLoginUser sysLoginUser = sysLoginUserServiceImpl.loadById(id);
        //返回json至前端的均返回ResultBean或者PageResultBean
        Date create_date = new Date();
        saleCustomer.setCreate_date(create_date);
        saleCustomer.setModify_date(create_date);
        saleCustomer.setCreator(sysLoginUser.getCreator());
        saleCustomer.setModifier(sysLoginUser.getModifier());
        //如果是管理员提交则不可修改,如说是员工提交则可以修改
        if (saleCustomer.getCreator() == 2){
            saleCustomer.setState("draft");
        }else if(saleCustomer.getCreator() == 1){
            saleCustomer.setState("effect");
        }
        saleCustomerServiceImpl.insertSingleCustomer(saleCustomer);
        return new ResultBean<String>("success");
    }
    /**
     * 删除客户信息
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult deleteCustomer(int id){
        List<SaleCustomer> saleCustomerList = saleCustomerServiceImpl.loadById1(id);
        for (SaleCustomer saleCustomer:saleCustomerList) {
            List<SaleBill> saleBillList = saleBillServiceImpl.loadById1(saleCustomer.getId());
            for (SaleBill saleBill:saleBillList) {
                saleBillServiceImpl.deleteSaleBill(saleBill.getCustomer_id());
            }
            saleCustomerServiceImpl.deleteCustomer(id);
        }
        return new ResultBean<String>("success");
    }
    /**
     * 获取客户ID
     * */
    @RequestMapping("/loadById.do")
    @ResponseBody
    public IResult getId(int id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SaleCustomer>(saleCustomerServiceImpl.loadById(id));
    }
    /**
     * 修改客户信息
     */
    @RequestMapping("/updateCustomer.do")
    @ResponseBody
    public IResult updateCustomer(@RequestBody SaleCustomer saleCustomer){
        //取到管理员ID
        int id = 1;
        SysLoginUser sysLoginUser = sysLoginUserServiceImpl.loadById(id);
        saleCustomer.setModifier(sysLoginUser.getModifier());
        Date modify_date = new Date();
        saleCustomer.setModify_date(modify_date);
        //进行判断
        if (saleCustomer.getModifier() == 1){
            saleCustomer.setState("effect");
        }
        saleCustomerServiceImpl.updateCustomer(saleCustomer);
        return new ResultBean<String>("success");
    }
    @ResponseBody
    @RequestMapping(value="fileUpload.do", produces = "application/text; charset=utf-8")
    public String UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return saleCustomerServiceImpl.ajaxUploadExcel(request, response);
    }
    /**
     * 查看订单记录
     */
    @RequestMapping("/loadByCusId")
    @ResponseBody
    public IResult loadByCusId(Integer id){
        return new ResultBean<SaleBill>(saleCustomerServiceImpl.loadByCusId(id));
    }
    /*
    * 选择客户添加
    * */
    @RequestMapping("/chooseCus")
    @ResponseBody
    public IResult chooseCus(){
        List<SaleCustomer> saleCustomers = saleCustomerServiceImpl.listChooseCustomer();
        return new ResultBean<Collection<SaleCustomer>>(saleCustomers);
    }
}
