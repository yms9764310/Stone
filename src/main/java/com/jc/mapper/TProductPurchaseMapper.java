package com.jc.mapper;

import com.jc.model.TProductsyspurchaseproduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TProductPurchaseMapper {
    int saveT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct);
    int removeT_Product_sys_purchase_product(int id);
    int updateT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct);
    List<TProductsyspurchaseproduct> listT_Product_sys_purchase_product(@Param("start") int start, @Param("end") int end,
                                                                        @Param("name")String name, @Param("kind")String kind);
    int  countT_Product_sys_purchase_product(@Param("name")String name,@Param("kind")String kind);
    TProductsyspurchaseproduct loadT_Product_sys_purchase_product(int id);
    int addBatchMembers( List<TProductsyspurchaseproduct> list);
}

