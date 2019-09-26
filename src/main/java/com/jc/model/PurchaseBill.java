package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 14:45
 */
//采购单/采办事项表
@Data
public class PurchaseBill implements Serializable {
    //主键
    private int id;
    //创建人
    private String creator;

    private String creatorName;

    private String creator_id;
    //创建时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //修改人
    private String modifier;
    //修改时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    //状态
    private String State;
    //采购人
    private String purchaseName;
    //采购人Id
    private int purchaseId;
    //入库时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date putInDate;
    //是否紧急
    private String emergent;
    //是否为采购单
    private String isBill;
    //总金额
    private String sumMoney;
    //预期时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectDate;
    //银行卡号
    private String AccountNo;
    //关联表人员表
    private SysUsers sysUsers;
    //关联资源表
    private SysResource sysResource;
    //关联采购单表
    private List<PurchaseBillDetail> purchaseBillDetailList;
}
