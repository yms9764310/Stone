package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * 年: 2019
 * 月: 09
 * 日: 23
 * 小时: 14
 * 分钟: 35
 *
 * @author 严脱兔
 */
@Data
public class LossBeans {
    private Integer id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private Integer state;
    private Integer product_id;
    private String product_name;
    //原始库存量
    private Double stock_number;
    //盘点数量
    private Double check_number;
    //损耗量
    private Double loss_number;
}
