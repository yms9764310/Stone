package com.jc.service;

import com.jc.model.PurchaseSupplier;
import com.jc.model.SysPurchaseProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 李文教
 * @date 2019/8/21 16:43
 */
public interface PurchaseSupplierService {
    //查询全部供应商以及搜索功能
    List<PurchaseSupplier> listSupplier(String page,String limit,String SysProductName,String name);
    //获取菜单大小
    int countGetAll();
    //添加供应商
    boolean insertSupplier(PurchaseSupplier purchaseSupplier);
    //查询商品名称
    List<SysPurchaseProduct> listSysPurchaseProduct();
    //查看商品
    List<PurchaseSupplier> loadProductSupplier(Integer id);
    //删除
    boolean deleteSupplier(Integer id);
    //根据id获取指定供应商信息
    List<PurchaseSupplier> loadPurchaseSupplier(Integer id);
    //修改指定供应商信息
    boolean updateSupplier(PurchaseSupplier purchaseSupplier);
    //导入
    String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response);

}
