package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 29
 * 小时: 14
 * 分钟: 25
 *
 * @author 严脱兔
 */
@Data
public class StoreCheckOut {
    private Integer id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private Integer product_id;
    private String product_name;
    private Double number;
    //可用数量    //可用数量=总数量-锁定量-待发货量
    private Double can_number;
    //锁定量         //订单锁定过久后，锁定量变为活动量
    private Double locking_number;
    //活动量
    private Double activity_number;
    //待发货量
    private Double delivered_number;
    //总数量
    private Double countnumber;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date checkout_date;
    //申请人
    private Integer checkout_user_id;
    private String checkout_user_name;
    private String source_kind;
    private String source_id;
}
