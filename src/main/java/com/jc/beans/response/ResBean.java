package com.jc.beans.response;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 16
 * 小时: 18
 * 分钟: 00
 *
 * @author 严脱兔
 */
public class ResBean<T> {
    private String code;
    private String msg;
    private int count;
    private List<T> data;

    public ResBean(String code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public ResBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
