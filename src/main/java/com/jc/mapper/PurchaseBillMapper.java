package com.jc.mapper;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 16:33
 */
@Repository
public interface PurchaseBillMapper {
    //查询采办事项
    List<PurchaseBill> listPurchaseBill(@Param("start")int start,
                                        @Param("end")int end,
                                        @Param("creatorName")String creatorName);
    //查询采办事项
    List<PurchaseBill> listPurchaseBillUser(@Param("start")int start,
                                        @Param("end")int end,
                                        @Param("creator")int creator,
                                            @Param("creatorName")String creatorName);
    //获取采办菜单总数,分页
    int countGet();
    //查询待审核
    List<PurchaseBill> listPurchaseCheck(@Param("start")int start,
                                         @Param("end")int end);
    //获取采购单菜单总数,分页
    int countGetAll();
    //查询采办商品详情
    List<PurchaseBillDetail> listPurchaseBillDetail();
    //查询商品表
    List<SysPurchaseProduct> listSysPurchaseProduct();
    //添加采办事项信息
    int insertPurchaseBill(PurchaseBill purchaseBill);
    //添加采办商品
    int insertPurchaseBillDetail(PurchaseBillDetail purchaseBillDetail);
    //根据id查询采办事项
    PurchaseBill loadPurchaseBill(@Param("id")int id);
    //删除采办事项
    int deletePurchaseBill(@Param("id")int id);
    int deletePurchaseBillDetail(@Param("billId")int billId);
    //编辑采办事项
    int updatePurchaseBill(PurchaseBill purchaseBill);
    int updatePurchaseBillDetail(PurchaseBillDetail purchaseBillDetail);
    //根据采购单id查询采办详情
    List<Map<String,Object>> listBillDeatil(Integer id);
    //查询采购人员
    List<SysUsers> listSysUsersName();
    //根据商品名查询供应商
    List<SupplierProduct> listPurchaseSupplieLike(int id);
    //查询全部采购单
    List<PurchaseBill> listPurchaseBillOrders(@Param("start")int start,@Param("end")int end);
    List<SupplierProduct> listSupplierProduct(@Param("productName")String productName,@Param("kind")String kind);
    //商品价格
    List<SupplierProduct> listSupplierPrice(int purchaseSupplierId,int productId);
    //修改采办事项
    int updatePurchaseBillAudit(PurchaseBill purchaseBill);
    //修改采办事项详细内容
    int updatePurchaseBillDetailAudit(PurchaseBillDetail purchaseBillDetail);
    //待完成,根据id查询
    PurchaseBill loadPurchaseBillCompleted(@Param("id")int id);
    //查询已审核的商品
    List<PurchaseBillDetail> listBillDetailAudited();
    //月统计金额、数量、种类
    List<PurchaseBillDetail> countPurchase(@Param("yearDate")int yearDate);
    //根据商品种类月统计金额、数量
    List<PurchaseBillDetail> countPurchaseProduct(@Param("productId")int productId,@Param("yearDate")int yearDate);
    //年统计金额、数量、种类
    List<PurchaseBillDetail> countPurchaseYear();
    //根据商品种类年统计金额、数量
    List<PurchaseBillDetail> countPurchaseProductYear(@Param("productId")int productId);
}
