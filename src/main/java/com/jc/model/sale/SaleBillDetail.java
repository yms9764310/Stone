package com.jc.model.sale;/*
 * @author 林锦杰
 * @date 2019/9/10 10:57
 * */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/*
 * 销售详细表
 * */
@Data
public class SaleBillDetail implements Serializable {
    private int id;//主键自增
    private int sale_bill_id;//销售订单ID
    private int product_id;//商品id
    private BigDecimal price;//价格
    private BigDecimal number;//数量
    private String unit;//单位 手动输入
}
