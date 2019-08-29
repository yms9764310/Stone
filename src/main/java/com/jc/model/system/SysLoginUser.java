package com.jc.model.system;/*
 * @author 林锦杰
 * @date 2019/8/27 16:42
 * */

import java.io.Serializable;
import java.util.Date;
//登录表
public class SysLoginUser implements Serializable {
    private int id;//主键
    private int creator;//创建人
    private Date create_date;//创建时间
    private int modifier;//修改人
    private Date modify_date;//修改时间
    private String state;//状态
    private String psd;//密码
    private int user_id;//用户id
    private String account_name;//账户名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
}
