package com.jc.model.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class SaleBill implements Serializable {
    /*
    *销售单表
    */
    private int id;//主键自增
    private int creator;//创建人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
    private int modifier;//修改人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;//修改时间
    private String state;//状态：draft可修改删除，effect不可修改删除,delivery已发货不可修改
    private String billNo;//订单编号
    private int saleId;//负责人
    private String address;//收货地址
    private BigDecimal saleMoney;//订单总金额
    private Date deliverDate;//交付时间
    private String settleType;//结算方式
    private int customerId;//客户
}
