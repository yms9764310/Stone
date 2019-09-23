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
    //出库单里的数量/盘点数量
    private Double check_number;
    private List<StorePutIn> storePutInlist;
    private List<StoreCheckOut> storeCheckOutlist;

    public Store(Integer product_id, Double number) {
        this.product_id = product_id;
        this.number = number;
    }

    public Store(Integer id, Integer product_id, String product_name, Double number, Double can_number, Double locking_number, Double activity_number, Double delivered_number, Double warn_number, Double check_number, List<StorePutIn> storePutInlist, List<StoreCheckOut> storeCheckOutlist) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.number = number;
        this.can_number = can_number;
        this.locking_number = locking_number;
        this.activity_number = activity_number;
        this.delivered_number = delivered_number;
        this.warn_number = warn_number;
        this.check_number = check_number;
        this.storePutInlist = storePutInlist;
        this.storeCheckOutlist = storeCheckOutlist;
    }

    public Store() {
    }
}
