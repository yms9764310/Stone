package com.jc.controller.salecontroller;/*
 * @author 林锦杰
 * @date 2019/8/30 15:24
 * */

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.SysLoginUser;
import com.jc.model.sale.SaleBill;
import com.jc.service.SysLoginUserService;
import com.jc.service.sale.SaleBillService;
import lombok.extern.slf4j.Slf4j;
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
    public IResult listCustomer(String page, String limit, Integer creator, Integer bill_no){
        List<SaleBill> saleBills = saleBillServiceImpl.listSaleBill(page,limit,creator,bill_no);
        return new PageResultBean<Collection<SaleBill>>(saleBills,saleBillServiceImpl.countGetAll());
    }
    /**
     * 获取客户ID
     * */
    @RequestMapping("/loadById.do")
    @ResponseBody
    public IResult loadById(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<SaleBill>(saleBillServiceImpl.loadById(id));
    }
    /**
     * 修改订单
     */
    @RequestMapping("/updateSaleBill.do")
    @ResponseBody
    public IResult updateSaleBill(@RequestBody SaleBill saleBill){
        //取到管理员ID
        int id = 1;
        SysLoginUser sysLoginUser = sysLoginUserServiceImpl.loadById(id);
        saleBill.setModifier(sysLoginUser.getModifier());
        Date modify_date = new Date();
        saleBill.setModify_date(modify_date);
        saleBillServiceImpl.updateSaleBill(saleBill);
        return new ResultBean<String>("success");
    }
    /*
     * 添加单个客户信息
     * */
    @RequestMapping(value = "/insertSaleBill.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertCustomer(@RequestBody SaleBill saleBill){
        int id = 2;
        SysLoginUser sysLoginUser = sysLoginUserServiceImpl.loadById(id);
        //SaleCustomer saleCustomer = new SaleCustomer();
        Date create_date = new Date();
        saleBill.setCreate_date(create_date);
        saleBill.setModify_date(create_date);
        saleBill.setCreator(sysLoginUser.getCreator());
        saleBill.setModifier(sysLoginUser.getModifier());
        saleBill.setSale_id(sysLoginUser.getCreator());
        //客户ID等于客户表ID
        //saleBill.setCustomer_id(saleCustomer.getId());
        //如果是管理员提交则不可修改,如说是员工提交则可以修改
        if (saleBill.getCreator() == 2){
            saleBill.setState("未审核");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        saleBill.setBill_no(rannum + str);
        saleBillServiceImpl.insertSaleBill(saleBill);
        return new ResultBean<String>("success");
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
    @RequestMapping(value = "/success.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateYes(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Integer>(saleBillServiceImpl.updateStateYes(id));
    }
    @RequestMapping(value = "/defeat.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateNo(Integer id){
        //返回json至前端的均返回ResultBean或者PageResultBean
        return new ResultBean<Integer>(saleBillServiceImpl.updateStateCancel(id));
    }
}
