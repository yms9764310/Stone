package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 22
 * 分钟: 05
 *
 * @author 严脱兔
 */
@Data
public class SysUsersExceptSale {
    private Integer id;
    private Integer creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private Integer modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String name;
    private String sex;
    private Integer age;
    private String phone;
    private Integer job_id;
    private String depart_id;
    private String depart_role_id;
}
