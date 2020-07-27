package com.swagger.demo.test2;

import com.swagger.demo.entity.User;
import com.swagger.demo.model.PageListData;
import com.swagger.demo.model.ResponseData;
import com.swagger.demo.utils.ResponseFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author 启哲
 * @创建时间2020/7/27
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * 测试Controller
 */
@Api(tags="用户模块2-用户查询")
@RestController
@RequestMapping("test2Controller")
public class Test2Controller {

    /**
     * 单个用户查询
     * @param name
     * @return
     */
    @ApiOperation(value="用户详情", notes="单个查询")
    @GetMapping("/getOne")
    public ResponseData<User> query(@RequestParam String name) {
        User user = User.builder().id("111").age("11").name("11").build();
        return ResponseFormat.retParam(200,user);
    }

    /**
     * 多个用户查询
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value="用户查询", notes="多个查询")
    @PostMapping("/query2")
    public ResponseData<PageListData<User>> query2(@RequestParam String name, String age) {
        User user = User.builder().id("111").age("11").name("11").build();
        List list = Arrays.asList(user);
        PageListData result =PageListData.builder().list(list).tatol(1).build();
        return ResponseFormat.retParam(200,result);
    }

    /**
     * 入参为实体时展示
     * @param user
     * @return
     */
    @ApiOperation(value="用户条件查询", notes="条件查询")
    @PostMapping("/query3")
    public ResponseData<User> query3(@RequestBody User user) {
        user = User.builder().id("111").age("11").name("11").build();
        return ResponseFormat.retParam(200,user);
    }



}
