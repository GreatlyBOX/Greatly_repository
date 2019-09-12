package com.example.helloWord.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr on 2017/12/20.
 * Spring Boot  学习第一节
 * @RestController 表示所有方法都以JOSN格式返回
 * 等价于@Controller+@ResponseBody
 */
@RestController
public class HelloController {
    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping("/hello")
    public String hrllo(){
        logger.info("测试日志");
        return "Hello Spring Boot ";
    }
    @RequestMapping("/hello1")
    public String hrllo1(){
        logger.info("测试日志");
        int a=1/0;
        return "Hello Spring Boot ";
    }
}
