package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.AccountHandleBill;
import com.jc.model.AccountPayBill;
import com.jc.service.AccountHandleBillService;
import com.jc.service.AccountPayBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/PayBill")
public class AccountPayBillController {
     @Autowired
    private AccountPayBillService accountPayBillService;

    @RequestMapping("/listPayBill")
    @ResponseBody
    //分页查询
    public IResult getAccountPayBill(String page, String limit){
        List<AccountPayBill> resultData = accountPayBillService.listAccountPayBill(page,limit);
        return new PageResultBean<Collection<AccountPayBill>>(resultData,accountPayBillService.countAccountPayBill());
    }

    @RequestMapping(value = "/addFile")
    @ResponseBody
    public String addFile(@RequestParam("file") CommonsMultipartFile uploadFile, HttpServletRequest request) {
        String filename = uploadFile.getOriginalFilename();
        AccountPayBill accountPayBill = new AccountPayBill();
        String path = request.getSession().getServletContext().getRealPath("uploadfile");
        if (request instanceof MultipartHttpServletRequest) {
            String filepath = path + File.separator + filename;
            accountPayBill.setPayment_voucher_path(filepath);
            File file = new File(path, filename);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                uploadFile.transferTo(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "ERROR";
            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }
            boolean isadded = accountPayBillService.savePayment_voucher_path(path);
            if (isadded) {
                return "SUCCESS";
            }
            return "ERROR";
        }
        return "ERROR";
    }
}
