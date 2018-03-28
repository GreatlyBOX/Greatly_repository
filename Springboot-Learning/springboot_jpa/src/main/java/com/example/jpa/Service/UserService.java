package com.example.jpa.Service;


import com.example.jpa.Model.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */

public interface UserService {
    List<User> findall();
    User findById(Long Id);
    int deleteUser(Long Id);
    User findById(User user);

    User findByName(String name);
    User findByNameAndPassword(String name,String password);

    public Page<User> page(int pageSize, int pageNum);
}
