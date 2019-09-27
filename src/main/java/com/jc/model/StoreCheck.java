package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private Integer role_id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String state;
    private Date modify_date;
    private String display_name;
    private String depart_name;
    private String check_user_id;
    private String check_user_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date begin_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date end_date;
    private Boolean is_task;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;
    private Integer number;
    private List<StoreCheckTaskDetail> storeCheckTaskDetailList;
    private Store store;

    public StoreCheck(String creator, Date create_date, String check_user_id,String state ,Date begin_date, Date end_date) {
        this.creator = creator;
        this.create_date = create_date;
        this.check_user_id = check_user_id;
        this.state = state;
        this.begin_date = begin_date;
        this.end_date = end_date;
    }

    public StoreCheck(String modifier, Date modify_date, String check_user_id, Date begin_date, String state,  Date end_date,Integer id) {
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.check_user_id = check_user_id;
        this.begin_date = begin_date;
        this.state = state;
        this.end_date = end_date;
        this.id = id;
    }

    public StoreCheck() {
    }

    public StoreCheck(Integer id, String state) {
        this.id = id;
        this.state = state;
    }
}
