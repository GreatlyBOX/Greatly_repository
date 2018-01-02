package com.n22;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr on 2018/1/2.
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
