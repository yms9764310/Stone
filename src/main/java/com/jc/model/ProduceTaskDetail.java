package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 24
 * 小时: 16
 * 分钟: 30
 *
 * @author 严脱兔
 */
@Data
public class ProduceTaskDetail {
    private int id;
    private String process_user_id;
    private int  produce_task_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date begin_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date end_date;
    private String process_id;
    private String product_id;
    private Double number;
    private String process_type;
    private String name;
}
