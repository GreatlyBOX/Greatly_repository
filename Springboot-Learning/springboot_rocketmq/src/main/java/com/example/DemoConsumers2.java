package com.example;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "demo-topic", consumerGroup = "demo_consumer")
public class DemoConsumers2 implements RocketMQListener<User> {
    @Override
    public void onMessage(User user) {
        System.out.println("Consumers2接收消息:" + user.toString());
    }
}
