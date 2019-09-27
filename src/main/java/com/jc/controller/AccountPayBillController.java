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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public IResult uploadImg(@RequestBody MultipartFile file, String type , String fileName, HttpServletRequest request) throws Exception{
        //文件存储位置
        String path="D:/UploadImg/";
        //这是一个访问路径(如果不配置是访问不到图片的)
        String basePath="/UploadImg/";

        String returnPath = "";
        //获取文件名称
        try {


            fileName = file.getOriginalFilename();  //prefix  suffix
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            //生成新的文件名
            String newFileName= UUID.randomUUID().toString()+suffix;
            //创建文件
            File targetFile = new File(path, newFileName);
            //是否存在
            if(!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            System.out.println("上传成功:"+basePath+newFileName);
//                return basePath+newFileName;

            returnPath = basePath+newFileName;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResultBean<String>(returnPath);
    }

}
