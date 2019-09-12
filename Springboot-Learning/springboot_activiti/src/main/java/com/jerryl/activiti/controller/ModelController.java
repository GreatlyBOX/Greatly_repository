package com.jerryl.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("model")
@Controller
public class ModelController {
    /**
     * 返回视图
     * @return
     */
    @RequestMapping(value = "userSelect")
    public String userSelect() {
        return "/userSelect";
    }
}
