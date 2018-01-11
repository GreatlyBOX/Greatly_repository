package com.example.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
@Entity
@Table(name="t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String password;
    private Date birday;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirday() {
        return birday;
    }

    public void setBirday(Date birday) {
        this.birday = birday;
    }
}
