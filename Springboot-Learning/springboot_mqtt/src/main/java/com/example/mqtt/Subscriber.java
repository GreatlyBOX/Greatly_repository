package com.example.mqtt;


import com.example.utils.Global;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * @Description 消费者
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
public class Subscriber {
    private  static final Logger logger = LoggerFactory.getLogger(Publisher.class);
    private static String topic= Global.getConfig("consumeTopic");
    private static String broker = Global.getConfig("mqAddress");
    private static String clientId = Global.getConfig("consumeClientId");
    public static void consume() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            logger.info("开始启动MQTT消费者");
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            client.connect(connOpts);
            client.subscribe(topic, (topic, message) -> {
                String temp= message.getPayload().toString();
                System.out.println(topic + " -> " + temp);
            });
        } catch (MqttException me) {
            logger.info("reason " + me.getReasonCode());
            logger.info("msg " + me.getMessage());
            logger.info("loc " + me.getLocalizedMessage());
            logger.info("cause " + me.getCause());
            logger.info("excep " + me);
            me.printStackTrace();
        }
    }

    public static void main(String[] args) {
        consume();
    }
}
