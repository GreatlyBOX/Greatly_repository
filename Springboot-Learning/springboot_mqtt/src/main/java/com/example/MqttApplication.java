package com.example;

import com.example.mqtt.Subscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * @Description MQTT DOMO
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2019/1/8
 */
@SpringBootApplication
public class MqttApplication {

    public static void main(String[] args) {
        Subscriber.consume();
        SpringApplication.run(MqttApplication.class, args);
    }

}
