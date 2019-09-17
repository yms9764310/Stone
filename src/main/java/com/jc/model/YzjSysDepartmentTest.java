package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class YzjSysDepartmentTest {
    private Integer id;//部门主键
    private Integer did;
    private Integer creator;//创建人
    private  String creator_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;//创建时间
    private Integer modifier;//修改人
    private  String modefier_name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modify_date;//修改时间
    private String state;//状态
    private Integer parent_id;//上级部门
    private String name;//部门名称
    private String parent_rew;
    public YzjSysDepartmentTest() {
    }


    public YzjSysDepartmentTest(Integer creator, Date create_date, Integer modifier, Date modify_date, String state, Integer parent_id, String name) {
        this.creator = creator;
        this.create_date = create_date;
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.state = state;
        this.parent_id = parent_id;
        this.name = name;
    }

    public YzjSysDepartmentTest(Integer id, Integer creator, Date create_date, Integer modifier, Date modify_date, String state, Integer parent_id, String name) {

        this.id = id;
        this.creator = creator;
        this.create_date = create_date;
        this.modifier = modifier;
        this.modify_date = modify_date;
        this.state = state;
        this.parent_id = parent_id;
        this.name = name;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
