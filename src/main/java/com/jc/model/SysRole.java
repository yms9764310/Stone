package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 14
 * 分钟: 56
 *
 * @author 严脱兔
 */
@Data
public class SysRole {
    private Integer id;
    private Integer creator;//创建人
    private  String creator_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private Integer modifier;//修改人
    private  String modefier_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private Integer department_id;
    private String  department;
    private String name;//角色名
    private String description;//描述

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
