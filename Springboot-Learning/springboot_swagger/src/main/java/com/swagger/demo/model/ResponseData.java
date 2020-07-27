package com.swagger.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;


/**
 * @author 启哲
 * @创建时间2020/7/27
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * 返回对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseData<T> implements Serializable{


    /**
     * 程序定义状态码
     */
    @ApiModelProperty("状态码 200 成功   500 失败")
    private int code;
    /**
     * 必要的提示信息
     */
    @ApiModelProperty("操作结果描述")
    private String message;
    /**
     * 业务数据
     */
    @ApiModelProperty("返回结果泛型")
    private T datas;


}
