package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李文教
 * @date 2019/8/22 10:57
 * 采购商品表
 */
@Data
public class SysPurchaseProduct implements Serializable {
    //主键,自增
    private int id;
    //创建人
    private String creator;
    //创建时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //修改人
    private String  modifier ;
    //修改时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    //状态
    private String state;
    //商品名
    private String name;
    //商品类别
    private String kind;
    //商品型号
    private String modelType;
    //规格
    private String standard;
    //描述
    private String description;
}
