package com.example.jpa.Service.Impl;



import com.example.jpa.Model.User;
import com.example.jpa.Repository.UserJpa;
import com.example.jpa.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserJpa userJpa;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<User> findall() {
        return userJpa.findAll();
    }

    /**
     * 根ID查询
     * @param Id
     * @return
     */
    @Override
    public User findById(Long Id) {
        return userJpa.findOne(Id);
    }

    /**
     * 根据Id删除
     * @param Id
     * @return
     */
    @Override
    public int deleteUser(Long Id) {
        int result=0;
        try {
            userJpa.delete(Id);
            result=1;
        }catch (Exception e){
        e.printStackTrace();
        }
        return result;
    }

    /**
     * 编写SQl查询
     * @param user
     * @return
     */
    @Override
    public User findById(User user) {
        return userJpa.findByUser(user.getName(),user.getPassword());
    }

    /**
     * 解析名称查询（单参）
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {
        return userJpa.findByName(name);
    }

    /**
     * 解析名称查询（多参）
     * @param name
     * @param password
     * @return
     */
    @Override
    public User findByNameAndPassword(String name, String password) {
        return userJpa.findByNameAndPassword(name,password);
    }

    /**
     * 分页查询
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public Page<User> page(int pageSize, int pageNum) {
        return userJpa.findAll(new PageRequest(pageSize, pageNum));
    }
}
