package com.jc.mapper;

import com.jc.model.PurchaseSupplier;
import com.jc.model.SupplierProduct;
import com.jc.model.SysPurchaseProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 李文教
 * @date 2019/8/21 16:35
 */
@Repository
public interface PurchaseSupplierMapper {
    //查询全部供应商
    List<PurchaseSupplier> listSupplier(@Param("start")int start,
                                        @Param("end")int end,
                                        @Param("creator") String creator,
                                        @Param(("name"))String name);
    //获取菜单大小
    int countGetAll();
    //添加供应商
    int insertSupplier(PurchaseSupplier purchaseSupplier);
    //添加供应商产品表
    int insertProduct(SupplierProduct supplierProduct);
    //添加采购商品表
    int insertSysProduct(SysPurchaseProduct sysPurchaseProduct);
    //查询商品名称
    List<SysPurchaseProduct> listSysPurchaseProduct();
    //查看商品
    List<PurchaseSupplier> loadProductSupplier(@Param("id")int id);
    //删除
    int deleteSupplier(@Param("id")int id);
    //查询全部供应商商品表信息
    List<SupplierProduct> listProductSupplier(@Param("id")int id);
    //删除供应商产品表
    int deletePruchase(int purchaseSupplierId);
    //根据id获取指定供应商信息
    List<PurchaseSupplier> loadPurchaseSupplier(@Param("id") int id);
    //修改指定供应商信息
    int updateSupplier(PurchaseSupplier purchaseSupplier);
    //修改供应商产品表
    int updateProduct(SupplierProduct supplierProduct);
    //查询供应商产品表，修改操作调用
    List<Map<String,Object>> listPruchaseSupplierProduct(Integer id);
    //删除供应商产品表，修改调用
    int deletePrudchaseSupplierProduct(int id);
    //查询全部采购商品表，Excel导入调用
    List<Map<String,Object>> listSysProduct();
    //修改采购商品表,Excel导入调用
    int updateSysProduct(SysPurchaseProduct sysPurchaseProduct);
}
