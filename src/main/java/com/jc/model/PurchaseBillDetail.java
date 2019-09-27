package com.jc.model;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 15:16
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 采购单的详细内容
 */
@Data
public class PurchaseBillDetail implements Serializable {
    //主键
    private int id;
    //商品名称
    private String productId;
    private String value;
    private String productName;
    //采购数量
    private String number;
    //订单价格
    private String price;
    //总金额
    private String sumMoney;
    //供应商
    private String supplierId;
    private String supplierName;
    //采购单Id
    private String billId;
    //关联供应商表
    private PurchaseSupplier purchaseSupplier;
    //关联采购商品表
    private SysPurchaseProduct sysPurchaseProduct;
    //统计金额
    private String money;
    //统计数量
    private String numberTotal;
    //统计种类
    private String kind;
    //时间
    private String createDate;
    //年份
    private String yearDate;

}
