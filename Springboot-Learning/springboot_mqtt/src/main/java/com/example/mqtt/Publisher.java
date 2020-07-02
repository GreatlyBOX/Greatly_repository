package com.example.mqtt;
import com.example.utils.Global;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * @Description 生产者
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
public class Publisher {
    private  static final Logger logger = LoggerFactory.getLogger(Publisher.class);
    public static void send(String topic, byte[] bytes) {
        //0:msg只发1次,并且不需要确认.  1:msg至少发1次,需要确认.   2.发且仅发1次,并且需要进行4次挥手.
        int qos = 1;
        String broker = Global.getConfig("mqAddress");
        String clientId = Global.getConfig("clientId");
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            //第一次启动是否清除session
            connOpts.setCleanSession(false);
            client.connect(connOpts);
            MqttMessage message = new MqttMessage(bytes);
            message.setQos(qos);
            client.publish(topic, message);
            client.disconnect();
            System.exit(0);
        } catch (MqttException me) {
            logger.info("reason " + me.getReasonCode());
            logger.info("msg " + me.getMessage());
            logger.info("loc " + me.getLocalizedMessage());
            logger.info("cause " + me.getCause());
            logger.info("excep " + me);
            me.printStackTrace();
        }
    }
}
