package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 21
 * 小时: 14
 * 分钟: 37
 *
 * @author 严脱兔
 */
@Data
public class SysUsers {
    private Integer id;
    private String creator;
    private Integer creator_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    private Integer modifier_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String name;
    private String sex;
    private Integer age;
    private String phone;
    private String job_id;
    private String depart_id;
    private String depart_role_id;
    private Double max_threshold;
    private SysRole sysRole;
    private SysUserRole sysUserRole;

    public Integer getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }

    public Integer getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(Integer modifier_id) {
        this.modifier_id = modifier_id;
    }

    public SysUsers(Integer id, String creator, Date modify_date, String name, String sex, Integer age, String phone, String job_id, String depart_id, String depart_role_id) {
        this.id = id;
        this.creator = creator;
        this.modify_date = modify_date;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.job_id = job_id;
        this.depart_id = depart_id;
        this.depart_role_id = depart_role_id;
    }

    public SysUsers() {
    }

    public SysUsers(String creator ,Date modify_date,Double max_threshold) {
        this.creator = creator;
        this.modify_date = modify_date;
        this.max_threshold = max_threshold;
    }
}
