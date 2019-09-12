package com.example.helloWord.Controller;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {

    }
    public User getUser(List<User> list){
        User user=new User();
        list.stream().forEach(user1 -> {
            user.setNum1(user.getNum1()+user1.getNum1());
            user.setNum2(user.getNum2()+user1.getNum2());
            user.setNum3(user.getNum3()+user1.getNum3());
        });
        return user;
    };
}
