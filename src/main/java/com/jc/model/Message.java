package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 年: 2019
 * 月: 08
 * 日: 26
 * 小时: 14
 * 分钟: 31
 *
 * @author 严脱兔
 */
@Data
public class Message {
    private Integer id;
    private Integer creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private Integer modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String message_type;
    private String source_type;
    private Integer source_id;
    private String message_content;
    private Integer from_user_id;
    private String from_user_name;
    private String to_user_ids;

    public Message(Date modify_date,String state,String message_type,String message_content, Integer from_user_id, String to_user_ids) {
        this.modify_date = modify_date;
        this.state = state;
        this.message_type = message_type;
        this.message_content = message_content;
        this.from_user_id = from_user_id;
        this.to_user_ids = to_user_ids;
    }

    public Message() {
    }
}
