package com.jc.model.sale;/*
 * @author 林锦杰
 * @date 2019/9/10 10:57
 * */

import com.jc.model.TProductsyspurchaseproduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
 * 销售详细表
 * */
@Data
public class SaleBillDetail implements Serializable {
    private int id;//主键自增
    private int sale_bill_id;//销售订单ID
    private int product_id;//商品id
    private Double price;//价格
    private Double number;//数量s
    private Double sum_money;//数量
    private String unit;//单位 手动输入
    private String name;
}
