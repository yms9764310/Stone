package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.AccountHandleBill;
import com.jc.model.PurchaseBill;
import com.jc.service.AccountHandleBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/HandleBill")
public class TaccountHandleBillController {
     @Autowired
    private AccountHandleBillService taccountHandleBillService;
    @RequestMapping("/listHandleBill")
    @ResponseBody
    //分页查询
    public IResult getaccountHandleBill(String page, String limit){

        List<AccountHandleBill> resultData = taccountHandleBillService.listAccountHandleBill(page,limit);
        return new PageResultBean<Collection<AccountHandleBill>>(resultData,taccountHandleBillService.countAccountHandleBill());
    }
    /*
    创建应付单
     */
    @RequestMapping("/saveHandleBill")
    @ResponseBody
    public IResult saveaccountHandleBill(@RequestBody AccountHandleBill accountHandleBill){
     return new ResultBean<Integer>(taccountHandleBillService.saveAccountHandleBill(accountHandleBill));
    }
    /*
    审核根据id查询
     */
    @RequestMapping("/loadHandleBill")
    @ResponseBody
    public IResult loadaccountHandleBill(Integer id){
     return new ResultBean<AccountHandleBill>(taccountHandleBillService.loadAccountHandleBill(id));
    }
 /*
    审核通过
     */
    @RequestMapping("/updateHandleBill")
    @ResponseBody
    public IResult updateaccountHandleBill(Integer id){
     return new ResultBean<Integer>(taccountHandleBillService.updateAccountHandleBill(id));
    }

}
