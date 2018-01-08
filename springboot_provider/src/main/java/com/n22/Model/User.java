package com.n22.Model;

import java.util.Date;

/**
*@知乎专栏  Spring Boot学习教程
*https://zhuanlan.zhihu.com/c_152148543
*@author 启哲
* Created by Mr on 2018/1/2.
*/


public class User {
    private String user_id;
    private String user_name;
    private Date create_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
