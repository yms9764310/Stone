package com.jc.model;

import lombok.Data;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 30
 * 小时: 16
 * 分钟: 53
 *
 * @author 严脱兔
 */
@Data
public class Store {
    private Integer id;
    private Integer product_id;
    private String product_name;
    //总数量
    private Double number;
    //可用数量    //可用数量=总数量-锁定量-待发货量
    private Double can_number;
    //锁定量         //订单锁定过久后，锁定量变为活动量
    private Double locking_number;
    //活动量
    private Double activity_number;
    //待发货量
    private Double delivered_number;
    //库存预警量
    private Double warn_number;
    //出库单里的数量
    private Double check_number;
    private List<StorePutIn> storePutInlist;
    private List<StoreCheckOut> storeCheckOutlist;


}
