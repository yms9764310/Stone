package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class YzJSysUsers {
    private int id;
    private int creator;//创建人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private int modeifier;//修改人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private String name;//姓名
    private String sex;//性别，下拉单选
    private int age;//年龄
    private String phone;//电话
    private int job_id;//岗位id
    private int depart_id;//部门id
    private int depart_role_id;//部门角色id
    private double max_threshold;
}
