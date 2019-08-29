package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class YzjSysLoginUser {
    private int id;
    private int creator;//创建人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private int modifier;//修改人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private String psd;//密码
    private int user_id;//用户id
    private String account_name;//账户名称
}
