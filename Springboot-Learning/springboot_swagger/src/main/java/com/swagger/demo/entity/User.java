package com.swagger.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 启哲
 * @创建时间2020/7/27
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * 实体类
 */
@Setter
@Getter
@Builder
public class User {
    @ApiModelProperty("用户ID")
    private String id;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户年龄")
    private String age;
}
