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
public class StorePutIn {
    private Integer id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String source_type;
    private String kind;
    private String standard;
    private String model_type;
    private String description;
    private String source_id;
    private String product_id;
    private String product_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date put_id_date;
    private Double put_in_number;
    //申请人
    private String put_in_user_id;
    private String put_in_user_name;
}
