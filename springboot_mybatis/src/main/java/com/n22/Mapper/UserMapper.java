package com.n22.Mapper;

import com.n22.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/2
 */
@Mapper
public interface UserMapper {
    String returnSql="user_id,user_name,create_time";
    String  resultSql="#{user_id},#{user_name},#{create_time}";
    @Select("select "+returnSql+" from user")
//    @Results({@Result(property = "id",column = "user_id"),@Result(property = "name",column = "user_name"),@Result(property = "time",column = "create_time")})
    List<User> getAll();
    @Insert("insert into user("+returnSql+") values("+resultSql+")")
    int insert(User user);
    @Delete("delete from user where user_id=#{id}")
    int deleteUser(String  id);
    @Select("select "+returnSql+" from user where user_id=#{id}")
    User getUserById(String id);
    @Update("update user set user_name=#{user_name},create_time=#{create_time} where user_id=#{user_id}")
    int updateUser(User user);

}
