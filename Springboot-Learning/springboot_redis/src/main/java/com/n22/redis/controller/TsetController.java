package com.n22.redis.controller;

import com.n22.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Package com.n22.redis.controller
 * @Author miaopeng
 * @Date 2019/04/09 15:12
 */
@RestController
public class TsetController {
    @Autowired
    RedisUtils redisUtils;
    @RequestMapping("test")
    public String test(String name){
        return redisUtils.get(name).toString();
    }
}
