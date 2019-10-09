package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 23
 * 小时: 15
 * 分钟: 49
 *
 * @author 严脱兔
 */
@Data
public class ProduceProcess {
    private Integer id;
    private Integer creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private Integer modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private Integer process_user_id;
    private String process_type;
    private String description;
    private Integer produce_id;

}
