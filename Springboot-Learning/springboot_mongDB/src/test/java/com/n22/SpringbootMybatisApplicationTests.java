package com.n22;


import com.n22.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void test(){
        User user=new User();
        user.setAge(1);
        user.setBirthday(new Date());
        user.setEmail("2456");
        user.setUclass("2456");
        user.setUserId("2456");
        user.setName("小明");
        mongoTemplate.save(user);


        Query query=new Query(Criteria.where("name").is(user.getName()));
        User user1 = mongoTemplate.findOne(query , User.class);


    }


}
