package com.example.springboot_docker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Package com.example.springboot_docker
 * @Author miaopeng
 * @Date 2019/09/27 15:57
 */
@RequestMapping("testController")
@RestController
public class TestController {
    @RequestMapping("test")
    public String test(){
    return "你好";
    }
}
