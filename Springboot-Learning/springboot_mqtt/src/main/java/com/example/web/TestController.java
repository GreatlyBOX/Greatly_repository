package com.example.web;


import com.example.mqtt.Publisher;
import com.example.utils.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程
 * @Description 生产者
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2019/1/8
 */
@RequestMapping("testController")
@RestController
public class TestController {
    private  static final Logger logger = LoggerFactory.getLogger(TestController.class);


    /**
     * @Description: 设备ID 组成 网关ID_节点ID_设备ID
     * @Author
     * @Date 2019/08/15 17:50
     * @Param
     * @Return
     * @Throws
     */
    @RequestMapping("sendMsg")
    public String electricalsafetySomeOne(String collectorId, String msg) throws Exception {
        try {
            Publisher.send(Global.getConfig("commandtopic"),msg.getBytes());
            return "成功";
        }catch (Exception e){
            logger.info("失败"+e);
            return "失败";
        }
    }
}
