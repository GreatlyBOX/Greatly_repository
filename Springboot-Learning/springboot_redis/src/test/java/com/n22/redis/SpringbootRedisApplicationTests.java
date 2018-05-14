package com.n22.redis;

import com.n22.redis.model.User;
import com.n22.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

    @Autowired
    RedisUtils redisUtils;

	@Test
	public void contextLoads() {
        redisUtils.set("test","Test");
        String key=redisUtils.get("test").toString();
        System.out.println("从Redis中取出数据："+key);

        User oneUser=new User();
        oneUser.setId("10086");
        oneUser.setName("中国移动");
        redisUtils.set(oneUser.getId(),oneUser);
        User towoUser= (User) redisUtils.get("10086");

        System.out.println("从Redis中取出数据：ID为"+towoUser.getId()+"姓名为"+towoUser.getName());

	}

}
