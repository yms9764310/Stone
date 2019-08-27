package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 14
 * 分钟: 29
 *
 * @author 严脱兔
 */
@Data
public class SysLoginUser {
    private Integer id;
    private Integer creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private Integer modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String psd;
    private String oldPwd;
    private String newPwd;
    private Integer user_id;
    private String account_name;
}
