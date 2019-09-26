package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AccountHandleBill {
    private Integer id;//主键
    private Integer creator;//创建人
    private  String creator_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private Integer modifier;//修改人
    private  String modify_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private Integer commit_user_id;
    private String commit_user_name;
    private Integer effect_user_id;
    private String  effect_user_name;
    private double sum_money;//金额
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pay_date;//应付日期
    private Integer account_no;//账号
    private String source_type;//来源但类型
    private Integer source_id;//来源单
}
