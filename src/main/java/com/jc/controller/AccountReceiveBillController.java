package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.AccountPayBill;
import com.jc.model.AccountReceiveBill;
import com.jc.service.AccountPayBillService;
import com.jc.service.AccountReceiveBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/ReceiveBill")
public class AccountReceiveBillController {
     @Autowired
    private AccountReceiveBillService accountReceiveBillServiceImpl;

    @RequestMapping("/listReceiveBill")
    @ResponseBody
    //分页查询
    public IResult getAccountReceiveBill(String page, String limit){
        List<AccountReceiveBill> resultData = accountReceiveBillServiceImpl.listAccountReceiveBill(page,limit);
        return new PageResultBean<Collection<AccountReceiveBill>>(resultData,accountReceiveBillServiceImpl.countAccountReceiveBill());
    }



}
