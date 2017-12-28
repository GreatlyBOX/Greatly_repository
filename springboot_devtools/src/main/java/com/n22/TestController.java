package com.n22;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr on 2017/12/28.
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public String test(){
        return "Hello Spring Boot111111111 ";
    }
}
