package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.*;
import com.jc.service.PurchaseBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 17:06
 */
@Controller
@Slf4j
@RequestMapping("/procurementBill")
public class PurchaseBillController {
    @Autowired
    PurchaseBillService purchaseBillService;

    //查询待采办事项
    @RequestMapping(value = "/listProcurement",method = RequestMethod.POST)
    @ResponseBody
    public IResult listProcurement(String page,String limit,String creator){
        List<PurchaseBill> list=purchaseBillService.listPurchaseBill(page,limit,creator);
        return new PageResultBean<Collection<PurchaseBill>>(list,purchaseBillService.countGet());
    }

    //查询采办商品详情
    @RequestMapping(value = "/listBillDetail",method = RequestMethod.POST)
    @ResponseBody
    public IResult listPurchaseBillDetail(){
        List<PurchaseBillDetail> list=purchaseBillService.listPurchaseBillDetail();
        return new ResultBean<Collection<PurchaseBillDetail>>(list);
    }
    //查询商品表
    @RequestMapping(value = "/listSysPurchaseProduct",method = RequestMethod.POST)
    @ResponseBody
    public IResult listSysPurchaseProduct(){
        List<SysPurchaseProduct> list=purchaseBillService.listPurchaseProduct();
        return new ResultBean<Collection<SysPurchaseProduct>>(list);
    }
    //添加采办事项
    @RequestMapping(value = "/insertPurchaseBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertPurchaseBill(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.insertPurchaseBill(purchaseBill));
    }

    //根据id查询采办事项
    @RequestMapping(value = "/loadPurchaseBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadPurchaseBill(Integer id){
        return new ResultBean<PurchaseBill>(purchaseBillService.loadPurchaseBill(id));
    }
    //删除采办事项
    @RequestMapping(value = "/deletePurchaseBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult deletePurchaseBill(Integer id){
        return new ResultBean<Boolean>(purchaseBillService.deletePurchaseBill(id));
    }
    //编辑采办事项
    @RequestMapping(value = "/updatePurchaseBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult updatePurchase(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updatePurchaseBill(purchaseBill));
    }

    //查询采购人员
    @RequestMapping(value = "/listSysUsersName",method = RequestMethod.POST)
    @ResponseBody
    public IResult listUsers(){
        return new ResultBean<Collection<SysUsers>>(purchaseBillService.listSysUsersName());
    }

    //根据商品名查询供应商
//    @RequestMapping(value = "/purchaseSupplierLike",method = RequestMethod.POST)
//    @ResponseBody
//    public IResult listSupplierLike( Integer id){
//       List<PurchaseSupplier> supplier=purchaseBillService.listPurchaseSupplierLike(id);
//       return new ResultBean<Collection<PurchaseSupplier>>(supplier);
//    }

    //查询全部采购单
    @RequestMapping(value = "/purchaseBillOrders",method = RequestMethod.POST)
    @ResponseBody
    public IResult listPurchaseBillOrders(String page,String limit){
        return new PageResultBean<Collection<PurchaseBill>>(purchaseBillService.listPurchaseBillOrders(page,limit),purchaseBillService.countGetAll());
    }

    @RequestMapping(value = "/supplierProduct",method = RequestMethod.POST)
    @ResponseBody
    public IResult supplierProduct(String productName,String kind){
        return new ResultBean<Collection<SupplierProduct>>(purchaseBillService.listSupplierProduct(productName,kind));
    }

    //商品价格
    @RequestMapping(value = "/supplierProductPrice",method = RequestMethod.POST)
    @ResponseBody
    public IResult listSupplierPrice(Integer purchaseSupplierId,Integer productId){
        return new ResultBean<Collection<SupplierProduct>>(purchaseBillService.listSupplierPrice(purchaseSupplierId,productId));
    }

    //审核
    @RequestMapping(value = "/purchaseBillAudit",method = RequestMethod.POST)
    @ResponseBody
    public IResult updatePurchaseBillAudit(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updatePurchaseBillAudit(purchaseBill));
    }

    //待完成，根据id查询
    @RequestMapping(value = "/loadCompletedBill",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadCompleted( Integer id){
        return new ResultBean<PurchaseBill>(purchaseBillService.loadPurchaseBillCompleted(id));
    }

    //创建订单
    @RequestMapping(value = "/updateCompleted",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateBillComleted(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updateBillComplete(purchaseBill));
    }

    //待完成的编辑操作
    @RequestMapping(value = "/editCompleted",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateCompleted(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updateCompletedBill(purchaseBill));
    }

    //采购单根据id获取
    @RequestMapping(value = "/loadBillOrders",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadBillOrders( Integer id){
        return new ResultBean<PurchaseBill>(purchaseBillService.loadBillOrders(id));
    }

    //采购单的编辑
    @RequestMapping(value = "/updateBillOrders",method = RequestMethod.POST)
    @ResponseBody
    public IResult updateBillOrders(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updateBillOrders(purchaseBill));
    }

    //采购单的审核根据id获取
    @RequestMapping(value = "/loadPurchaseBillOrders",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadPurchaseBillOrders( Integer id){
        return new ResultBean<PurchaseBill>(purchaseBillService.loadPurchaseBillOrders(id));
    }

    //采购单的审核
    @RequestMapping(value = "/updatePurchaseBillOrders",method = RequestMethod.POST)
    @ResponseBody
    public IResult updatePurchaseBillOrders(@RequestBody PurchaseBill purchaseBill){
        return new ResultBean<Boolean>(purchaseBillService.updatePurchaseBillOrders(purchaseBill));
    }

    //查询已审核的商品
    @RequestMapping(value = "/listPurchaseBillAudited",method = RequestMethod.POST)
    @ResponseBody
    public IResult listPurchaseBillAudited(){
        return new ResultBean<Collection<PurchaseBillDetail>>(purchaseBillService.listBillDetailAudited());
    }

    //统计采购金额、数量、种类
    @RequestMapping(value = "/countPurchase",method = RequestMethod.POST)
    @ResponseBody
    public IResult countPurchase(){
        return new ResultBean<Collection<PurchaseBillDetail>>(purchaseBillService.countPurchase());
    }
}
