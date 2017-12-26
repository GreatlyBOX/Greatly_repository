package com.n22.Model;




import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Mr on 2017/12/25.
 * @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
 */
public class User {
    private String id;
    private String name;
    private String age;
    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
    private Date birthday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
