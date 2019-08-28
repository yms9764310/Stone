package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 李文教
 * @date 2019/8/21 16:22
 * 供应商表
 */
@Data
public class PurchaseSupplier implements Serializable {
    //id,自增
    private int id;
    //创建人
    private String creator;
    //创建时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //修改人
    private String modifier ;
    //修改时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    //状态
    private String state;
    //公司名
    private String companyName;
    //联系人
    private String contactName;
    //联系电话
    private String contactPhone;
    //公司地址
    private String address;

    private SysResource sysResource;

    private List<SupplierProduct> supplierProductList;

    private List<SysPurchaseProduct> sysPurchaseProductList;

}
