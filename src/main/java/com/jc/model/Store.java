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
    private Double number;
    private Double warn_number;
    private List<StorePutIn> storePutInlist;
    private List<StoreCheckOut> storeCheckOutlist;
}
