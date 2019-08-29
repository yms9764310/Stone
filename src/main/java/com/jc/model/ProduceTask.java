package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 24
 * 小时: 16
 * 分钟: 25
 *
 * @author 严脱兔
 */
@Data
public class ProduceTask {
    private Integer id;
    private String creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date create_date;
    private String modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modify_date;
    private String state;
    private String bom_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date begin_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date end_date;
    private String description;
    private String process_user_id;
    private String process_type;
    private List<ProduceProcess> produceProcesses;
    private ProduceTaskDetail produceTaskDetail;


}
