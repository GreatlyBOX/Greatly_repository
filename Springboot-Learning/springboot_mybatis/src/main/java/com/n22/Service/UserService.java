package com.n22.Service;


import com.n22.Model.User;

import java.util.List;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/2
 */

public interface UserService {
 List<User> getAll();
 User getUserById(String id);
 int  insertUser(User user);
 int  delUserById(String id);
 int  updateUserById(User user);
}
