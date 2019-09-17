package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 09
 * 日: 09
 * 小时: 09
 * 分钟: 28
 *
 * @author 严脱兔
 */
@Data
public class StoreWarn {
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
    private Double warn_number;
    private String warn_user_id;


}
