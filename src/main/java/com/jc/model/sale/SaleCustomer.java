package com.jc.model.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SaleCustomer implements Serializable {
    /*
    * 客户表
    */
    private int id;//主键自增
    private int creator;//创建人
    //时间要加注解
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private int modifier;//修改人
    //时间要加注解
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态：draft可修改删除，effect不可修改删除
    private String name;//客户名字
    private String address;//客户地址
    private String phone;//联系方式电话
    private String company;//所属机构-输入公司

    public SaleCustomer(int creator, Date create_date, int modifier, Date modify_date, String state, String name, String address, String phone, String company) {
        this.creator = creator;
        this.create_date = create_date;
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.state = state;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.company = company;
    }
    public SaleCustomer(int id,int creator, Date create_date, int modifier, Date modify_date, String state, String name, String address, String phone, String company) {
        this.id = id;
        this.creator = creator;
        this.create_date = create_date;
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.state = state;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.company = company;
    }

    public SaleCustomer() {
    }
}
