package com.n22.Service.Impl;

import com.n22.Mapper.UserMapper;
import com.n22.Model.User;
import com.n22.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/2
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserMapper userMapper;
    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int delUserById(String id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUser(user);
    }
}
