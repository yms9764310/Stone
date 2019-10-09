package com.jc.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 年: 2019
 * 月: 08
 * 日: 14
 * 小时: 18
 * 分钟: 06
 *
 * @author 严脱兔
 */
@Data
public class Student implements Serializable {
    private int sid;
    private String sname;
    private String sex;
    private String clazz;
    private int password;
}
