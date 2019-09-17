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
    private Integer creator;//创建人
    private  String creator_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private Integer modifier;//修改人
    private  String modefier_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private String name;//姓名
    private String sex;//性别：下拉单选
    private Integer age;//年龄
    private String phone;//电话
    private Integer job_id;//岗位id
    private String jobName;
    private Integer depart_id;//部门Id
    private String departName;
    private Integer depart_role_id;//部门角色id
    private String roleName;
    private Double max_threshold;//



    private SysRole sysRole;
    private SysUserRole sysUserRole;
}
