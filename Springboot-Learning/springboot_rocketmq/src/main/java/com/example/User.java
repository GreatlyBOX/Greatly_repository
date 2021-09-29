package com.example;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    String userName;
    String userId;

    public User(){

    }

    public User(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "demoEntity{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
