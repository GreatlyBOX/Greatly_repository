package com.example.jpa.Repository;



import com.example.jpa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
public interface UserJpa extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,Serializable {
    @Query("from User where name=:name and password=:password")
    User findByUser(@Param("name") String name, @Param("password") String password);

    /**
     * 命名查询
     * @param name
     * @return
     */
    User findByName(String name);
    /**
     * 命名查询
     * @param name,password
     * @return
     */
    User findByNameAndPassword(String name,String password);


}
