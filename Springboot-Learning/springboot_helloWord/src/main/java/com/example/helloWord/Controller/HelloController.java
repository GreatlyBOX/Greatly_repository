package com.example.helloWord.Controller;

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
    @RequestMapping("/hello")
    public String hrllo(){
        return "Hello Spring Boot ";
    }

}
