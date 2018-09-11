package com.soecode.lyf.util.entity.token;

import java.io.Serializable;

/**
 * Created by wxl on 2018/6/19.
 */
public class UserTokenData implements Serializable {
    private static final long serialVersionUID = 2724888087391664167L;

    private String user_name;//用户姓名
    private String user_studentId;//用户学号
    private String user_iphone;//用户手机号
    private String user_id;//用户id

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_studentId() {
        return user_studentId;
    }

    public void setUser_studentId(String user_studentId) {
        this.user_studentId = user_studentId;
    }

    public String getUser_iphone() {
        return user_iphone;
    }

    public void setUser_iphone(String user_iphone) {
        this.user_iphone = user_iphone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
