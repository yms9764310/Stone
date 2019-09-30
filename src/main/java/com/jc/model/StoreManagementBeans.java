package com.jc.model;

import lombok.Data;

/**
 * 年: 2019
 * 月: 09
 * 日: 30
 * 小时: 09
 * 分钟: 15
 *
 * @author 严脱兔
 */
@Data
public class StoreManagementBeans {
    private Integer id;
    private String name;
    private Integer depart_role_id;
    private String role_name;
    private Integer depart_id;
    private String department_name;
}
