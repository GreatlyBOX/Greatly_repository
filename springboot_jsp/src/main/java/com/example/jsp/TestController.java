package com.example.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("index")
    public String test(){
        return "index";
    }

    /**
     * 不要在类上标@RestController
     * 会把类的返回结果都变成JSON
     * 视图解析也就不会解析到页面了
     * 会直接返回页面
     * @return
     */
    @RequestMapping("test")
    @ResponseBody
    public String test1(){
        return "index";
    }
}
