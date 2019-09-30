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
    List<PurchaseBill> listPurchaseBill(String page,String limit,Integer creator,String name);
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
    //查询全部采购单
    List<PurchaseBill> listPurchaseBillOrders(String page,String limit);
    //查询已审核商品
    List<PurchaseBillDetail> listBillDetailAudited();

    List<SupplierProduct> listSupplierProduct(String productName,String kind);
    //商品价格
    List<SupplierProduct> listSupplierPrice(Integer purchaseSupplierId,Integer productId);
    //审核采办事项
    boolean updatePurchaseBillAudit(PurchaseBill purchaseBill);
    //待完成,根据id查询
    PurchaseBill loadPurchaseBillCompleted(Integer id);
    //待完成,创建订单
    boolean updateBillComplete(PurchaseBill purchaseBill);
    //待完成的编辑操作
    boolean updateCompletedBill(PurchaseBill purchaseBill);
    //采购单信息根据id获取
    PurchaseBill loadBillOrders(Integer id);
    //采购单的编辑
    boolean updateBillOrders(PurchaseBill purchaseBill);
    //采购单的审核,根据id获取信息
    PurchaseBill loadPurchaseBillOrders(Integer id);
    //采购单的审核
    boolean updatePurchaseBillOrders(PurchaseBill purchaseBill);
    //根据年份统计月金额、数量、种类
    List<PurchaseBillDetail> countPurchase(Integer yearDate);
    //根据商品年份种类月统计金额、数量
    List<PurchaseBillDetail> countPurchaseProduct(Integer productId,Integer yearDate);
    //年统计金额、数量、种类
    List<PurchaseBillDetail> countPurchaseYear();
    //根据商品种类年统计金额、数量
    List<PurchaseBillDetail> countPurchaseProductYear(Integer productId);
    //手动关闭订单
    boolean updateCloseBill(PurchaseBill purchaseBill);
    //根据id查询关闭订单的原因
    PurchaseBill loadReasonBill(Integer id);
    //查询采购历史记录
    List<PurchaseBill> listBillOrdersLike(String purchaseName,String putInDate,String expectDate);
}
