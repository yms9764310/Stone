package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class YzjSysResource {
    private int id;
    private int creator;//创建人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private int modifier;//修改人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private int parent_id;//上级资源id
    private String kind;//资源类型
    private String sequence;//资源编号
    private String display_name;//显示名称

}
