package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TysProduceBom {
    private int id;
    private int creator;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
    private int modifier;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;
    private String state;
    private int product_id;
    private int number;
    private String name;
    private String kind;
    private String model_type;
    private String standard;
    private String description ;
    List<TysProduceBomDetail> listBomDetail;
}
