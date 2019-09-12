//package com.n22.redis;
//
//
//import com.n22.redis.model.User;
//
//import com.n22.redis.utils.RedisUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringbootRedisApplicationTests {
//
//    @Autowired
//    RedisUtils redisUtils;
//
//	@Test
//	public void contextLoads() throws JsonProcessingException {
//        //将对象转成json串保存
//        ObjectMapper mapper = new ObjectMapper();
//        redisUtils.set("test","Test");
////        String key=redisUtils.get("test").toString();
////        System.out.println("从Redis中取出数据："+key);
//
//        User oneUser=new User();
//        oneUser.setId("100862");
//        oneUser.setName("中国移动");
//        String tokenclam = mapper.writeValueAsString(oneUser);
//        //使用加密前token存储token令牌
//        redisUtils.set("100862", oneUser,60*60*24L);
//        Object towoUser= redisUtils.get("100862");
//        System.out.println("从Redis中取出数据："+towoUser.toString());
//
//        Map<String,String> map=new HashMap<>();
//        map.put("id","id");
//        map.put("name","name");
//        redisUtils.set("map", map,60*60*24L);
//        List list=new ArrayList();
//        list.add("nan1");
//        list.add("nan1");
//        redisUtils.set("list", list,60*60*24L);
//	}
//
//}
