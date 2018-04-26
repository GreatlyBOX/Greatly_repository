package com.n22.Controller;


import com.n22.Model.User;
import com.n22.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 启哲
 * @创建时间2018/1/2
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 */
@RestController
public class UserController {
    @Autowired(required = false)
    UserService userService;
    @RequestMapping("selectUsers")
    public List getUsers(){
        List users=userService.getAll();
        return users;

    }
    @RequestMapping("selectUser")
    public User getUserOne(String id){
        User user=userService.getUserById(id);
        return user;

    }

}
