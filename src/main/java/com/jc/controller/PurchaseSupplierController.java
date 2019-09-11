package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.PurchaseSupplier;
import com.jc.model.SysPurchaseProduct;
import com.jc.service.PurchaseSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 李文教
 * @date 2019/8/21 17:28
 */
@Slf4j
@Controller
@RequestMapping("/purchaseSupplier")
public class PurchaseSupplierController {
    @Autowired
    PurchaseSupplierService purchaseSupplierService;

    //查询全部供应商记录以及搜索的功能
    @RequestMapping(value = "/listSupplier",method = RequestMethod.POST)
    @ResponseBody
    public IResult listPurchase(String page,String limit,String SysProductName,String name){
        List<PurchaseSupplier> list=purchaseSupplierService.listSupplier(page,limit,SysProductName,name);
        return new PageResultBean<Collection<PurchaseSupplier>>(list,purchaseSupplierService.countGetAll());
    }

    //添加供应商的功能
    @RequestMapping(value = "/insertSupplier",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertPurchase(@RequestBody PurchaseSupplier purchaseSupplier){
        return new ResultBean<Boolean>(purchaseSupplierService.insertSupplier(purchaseSupplier));
    }
    //查询商品名称
    @RequestMapping(value = "/listPurchaseProduct",method = RequestMethod.POST)
    @ResponseBody
    public IResult listPurchaseProduct(){
        return new ResultBean<Collection<SysPurchaseProduct>>(purchaseSupplierService.listSysPurchaseProduct());
    }

    //删除
    @RequestMapping(value = "/deleteSupplier",method = RequestMethod.POST)
    @ResponseBody
    public IResult deletePurchase(Integer id){
        return new ResultBean<Boolean>(purchaseSupplierService.deleteSupplier(id));
    }

    //根据id获取指定供应商信息
    @RequestMapping(value = "/loadSupplier",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadPurchaseSupplier(Integer id){
        return new ResultBean<Collection<PurchaseSupplier>>(purchaseSupplierService.loadPurchaseSupplier(id));
    }

    //修改指定供应商信息
    @RequestMapping(value = "/updateSupplier",method = RequestMethod.POST)
    @ResponseBody
    public IResult updatePurchaseSupplier(@RequestBody PurchaseSupplier purchaseSupplier){
        return new ResultBean<Boolean>(purchaseSupplierService.updateSupplier(purchaseSupplier));
    }

    //POI导入Excel数据表格
//    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
//    @ResponseBody
//    public boolean impotrExcel(@RequestParam("file") MultipartFile file) throws Exception {
//            return purchaseSupplierService.importExcel(file);
//
//        }
    @ResponseBody
    @RequestMapping(value="/fileUpload.do",method = RequestMethod.POST)
    public IResult UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ResultBean<String>(purchaseSupplierService.ajaxUploadExcel(request, response));
    }

    //查看商品
    @RequestMapping(value = "/loadProduct",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadProduct(Integer id){
        return new ResultBean<Collection<PurchaseSupplier>>(purchaseSupplierService.loadProductSupplier(id));
    }
}
