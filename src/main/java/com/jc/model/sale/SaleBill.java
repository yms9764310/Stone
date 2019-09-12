package com.jc.model.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/*
 *销售单表
 */
@Data
public class SaleBill implements Serializable {
    private int id;//主键自增
    private int creator;//创建人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private int modifier;//修改人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态：draft可修改删除，effect不可修改删除,delivery已发货不可修改
    private String bill_no;//订单编号
    private int sale_id;//负责人
    private String address;//收货地址
    private BigDecimal sale_money;//订单总金额
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliver_date;//交付时间
    private String settle_type;//结算方式
    private int customer_id;//客户

    public SaleBill() {
    }
}
