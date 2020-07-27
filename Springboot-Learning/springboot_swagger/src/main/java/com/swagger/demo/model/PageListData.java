package com.swagger.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 启哲
 * @创建时间2020/7/27
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * 分页返回对象
 */
@Setter
@Getter
@Builder
public class PageListData<T> {
    @ApiModelProperty("返回条数")
    private int tatol;
    @ApiModelProperty("集合")
    private List<T> list;
}