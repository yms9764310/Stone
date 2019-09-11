package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 23
 * 小时: 17
 * 分钟: 40
 *
 * @author 严脱兔
 */
@Data
public class ToDoList {
    private Integer id;
    private String creator;
    private String put_in_user_id;
    private String user_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date date;
    private String modifier;
    private String source_type;
    private String product_id;
    private String product_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date checkout_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date put_id_date;
    private String state;
    private String typename;

    public ToDoList(Integer id, String creator, String put_in_user_id, String user_id, Date create_date, Date date, String modifier, Date modify_date, Date checkout_date, Date put_id_date, String state, String typename) {
        this.id = id;
        this.creator = creator;
        this.put_in_user_id = put_in_user_id;
        this.user_id = user_id;
        this.create_date = create_date;
        this.date = date;
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.checkout_date = checkout_date;
        this.put_id_date = put_id_date;
        this.state = state;
        this.typename = typename;
    }

    public ToDoList() {
    }
}
