package com.n22.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.n22.Mapper.UserMapper;
import com.n22.Model.User;
import com.n22.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<User> findUserByName(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.getAll();
    }

    @Override
    public User findUserById(User user) {
        return userMapper.findUserById(user);
    }

    @Override
    public User findUserByName(User user) {
        Map<String,Object> maps=new HashMap<>();
        maps.put("user_name",user.getUser_name());
        maps.put("user_id",user.getUser_id());
        return userMapper.findUserByName(maps);
    }
}
