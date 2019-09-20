package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class YzjSysjob {
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
    private String name;//岗位名称
    private Integer parent_id;//上级部门
    private String parent_rew;
}
