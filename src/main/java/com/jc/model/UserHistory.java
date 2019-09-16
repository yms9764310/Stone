package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 09
 * 日: 12
 * 小时: 10
 * 分钟: 21
 *
 * @author 严脱兔
 */
@Data
public class UserHistory {
    private Integer id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private Date visitTime;
    private String ip;
    private String url;
    private Long executionTime;
    private String  method;
    //操作说明
    private String description;
    //操作人ID
    private Integer user_id;
    //操作人名字
    private String user_name;
}
