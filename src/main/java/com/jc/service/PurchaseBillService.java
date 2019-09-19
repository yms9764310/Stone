package com.jc.service;

import com.jc.model.*;

import java.util.List;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 17:02
 */
public interface PurchaseBillService {
    //查询全部采办事项
    List<PurchaseBill> listPurchaseBill(String page,String limit,String creator);
    //获取采办菜单大小
    int countGet();
    //查询待审核
    List<PurchaseBill> listPurchaseCheck(String page,String limit);
    int countGetAll();
    //查询采办商品详情
    List<PurchaseBillDetail> listPurchaseBillDetail();
    //查询商品表
    List<SysPurchaseProduct> listPurchaseProduct();
    //添加采办事项信息
    Boolean insertPurchaseBill(PurchaseBill purchaseBill);
    //根据id查询采办事项
    PurchaseBill loadPurchaseBill(Integer id);
    //删除采办事项
    boolean deletePurchaseBill(Integer id);
    //编辑采办事项
    boolean updatePurchaseBill(PurchaseBill purchaseBill);
    //查询采购人员
    List<SysUsers> listSysUsersName();
    //根据商品名查询供应商

    //查询全部采购单
    List<PurchaseBill> listPurchaseBillOrders(String page,String limit);

    List<SupplierProduct> listSupplierProduct(String productName,String kind);
    //商品价格
    List<SupplierProduct> listSupplierPrice(Integer purchaseSupplierId,Integer productId);
    //审核采办事项
    boolean updatePurchaseBillAudit(PurchaseBill purchaseBill);
}
