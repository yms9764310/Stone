package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.AccountHandleBill;
import com.jc.model.AccountReceiveHandleBill;
import com.jc.service.AccountReceiveHandleBillService;
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
@RequestMapping("/ReceiveHandleBill")
public class AccountReceiveHandleBillController {


        @Autowired
        private AccountReceiveHandleBillService accountReceiveHandleBillService;

        @RequestMapping("/listReceiveHandleBill")
        @ResponseBody
        //分页查询
        public IResult getAccountReceiveHandleBill(String page, String limit) {

            List<AccountReceiveHandleBill> resultData = accountReceiveHandleBillService.listAccountReceiveHandleBill(page, limit);
            return new PageResultBean<Collection<AccountReceiveHandleBill>>(resultData, accountReceiveHandleBillService.countAccountReceiveHandleBill());

    }
    /*
  创建应收单
   */
    @RequestMapping("/saveReceiveHandleBill")
    @ResponseBody
    public IResult saveAccountReceiveHandleBill(@RequestBody AccountReceiveHandleBill accountReceiveHandleBill){
        return new ResultBean<Integer>(accountReceiveHandleBillService.saveAccountReceiveHandleBill(accountReceiveHandleBill));
    }
    /*
  审核根据id查询
   */
    @RequestMapping("/loadReceiveHandleBill")
    @ResponseBody
    public IResult loadAccountReceiveHandleBill(Integer id){
        return new ResultBean<AccountReceiveHandleBill>(accountReceiveHandleBillService.loadAccountReceiveHandleBill(id));
    }
}
