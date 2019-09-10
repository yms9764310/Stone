package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 09
 * 日: 09
 * 小时: 16
 * 分钟: 09
 *
 * @author 严脱兔
 */
@Data
public class StoreCheck {
    private Integer id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String display_name;
    private String check_user_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date begin_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date end_date;
    private Boolean is_task;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;


}
