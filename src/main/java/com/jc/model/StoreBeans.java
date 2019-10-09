package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jc.utils.ExcelVOAttribute;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 20
 * 小时: 22
 * 分钟: 11
 *
 * @author 严脱兔
 */
@Data
public class StoreBeans {
    @ExcelVOAttribute(name = "盘点人", column = "A")
    private String check_user_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ExcelVOAttribute(name = "开始时间", column = "B")
    private Date begin_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ExcelVOAttribute(name = "结束时间", column = "C")
    private Date end_date;
    private String product_name;
    private Double check_number;
    private Double number;
    private List<ProductNumberBeans> productNumberBeansList;

}
