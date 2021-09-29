package com.example;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoProducers {
    @Autowired
    private RocketMQTemplate template;

    @RequestMapping("/producer")
    public String producersMessage() {
        User user = new User("sharfine", "123456789");
        template.convertAndSend("demo-topic", user);
        return JSON.toJSONString(user);
    }
}
