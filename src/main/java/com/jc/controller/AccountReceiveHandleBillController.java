package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.model.AccountReceiveHandleBill;
import com.jc.service.AccountReceiveHandleBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
