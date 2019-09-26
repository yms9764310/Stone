package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.model.AccountHandleBill;
import com.jc.service.AccountHandleBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public IResult getPermissions(String page, String limit){

        List<AccountHandleBill> resultData = taccountHandleBillService.listAccountHandleBill(page,limit);
        return new PageResultBean<Collection<AccountHandleBill>>(resultData,taccountHandleBillService.countAccountHandleBill());
    }
}
