package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李文教
 * @date 2019/8/22 10:51
 * 系统资源表
 */
@Data
public class SysResource implements Serializable {
    //主键,自增
    private int id;
    //创建人
    private int creator;
    //创建时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //修改人
    private int modifier;
    //修改时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    //上级资源id
    private int parent_id;
    //资源类型
    private String kind;
    //资源编号
    private String sequence;
    //显示名称
    private String displayName;
}
