package com.jc.service;

import com.jc.model.TProductsyspurchaseproduct;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TProductPurchaseService {
    int saveT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct);
    int removeT_Product_sys_purchase_product(int id);
    int updateT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct);
    List<TProductsyspurchaseproduct> listT_Product_sys_purchase_product(String page, String limit, String name, String kind);
    int  countT_Product_sys_purchase_product(String name,String kind);
    TProductsyspurchaseproduct loadT_Product_sys_purchase_product(int id);
    public Map<String, Object> importExcel(MultipartFile file)throws Exception;
    List<TProductsyspurchaseproduct> listTProductsyspurchaseproduct();
    List<TProductsyspurchaseproduct> listTProductsyspurchaseproductMaterialName();

}
