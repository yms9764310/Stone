package com.jc.model;

import lombok.Data;

/**
 * 年: 2019
 * 月: 09
 * 日: 10
 * 小时: 09
 * 分钟: 49
 *
 * @author 严脱兔
 */
@Data
public class StoreCheckTaskDetail {
    private Integer id;
    private Integer product_id;
    private String product_name;
    private Integer check_id;
    private Double check_number;
    private String name;
    private Integer value;
    private Integer number;


    public StoreCheckTaskDetail(Integer product_id, Integer check_id) {
        this.product_id = product_id;
        this.check_id = check_id;
    }

    public StoreCheckTaskDetail() {
    }
}
