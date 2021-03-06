package com.jc.model;/*
 * @author 林锦杰
 * @date 2019/9/23 15:26
 * */

import lombok.Data;

@Data
public class YzjSysAuthorityRole {
    private int id;
    private int role_id;//角色id
    private int authority_id;//权限id
    private int enable;//
    private String read;//读
    private int write;//写
    private int create;//创建
    private int delete;//删除
}
