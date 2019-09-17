package com.jc.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李文教
 * @date 2019/8/22 11:01
 * 供应商产品表
 */
@Data
public class SupplierProduct implements Serializable {
    //主键,自增
    private int id;
    //供应商id
    private int purchaseSupplierId;
    //商品id
    private int productId;
    //可供应最大数量
    private String maxNumber;
    //成本价
    private String price;

    private  String productName;
}
