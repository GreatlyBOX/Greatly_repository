package com.swagger.demo.utils;



import com.swagger.demo.model.ResponseData;

import java.util.HashMap;
import java.util.Map;
/**
 * @author 启哲
 * @创建时间2020/7/27
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 * 返回对象构建器
 */

public class ResponseFormat {

    private static Map<Integer,String> messageMap = new HashMap<>();
    //初始化状态码与文字说明
    static {
        /* 成功状态码 */
        messageMap.put(200, "成功");

        /* 服务器错误 */
        messageMap.put(1000,"服务器错误");

        /* 参数错误：10001-19999 */
        messageMap.put(10001, "参数无效");
        messageMap.put(10002, "参数为空");
        messageMap.put(10003, "参数类型错误");
        messageMap.put(10004, "参数缺失");

        /* 用户错误：20001-29999*/
        messageMap.put(20001, "用户未登录");
        messageMap.put(20002, "账号不存在或密码错误");
        messageMap.put(20003, "账号已被禁用");
        messageMap.put(20004, "用户不存在");
        messageMap.put(20005, "用户已存在");

        /* 业务错误：30001-39999 */
        messageMap.put(30001, "某业务出现问题");

        /* 系统错误：40001-49999 */
        messageMap.put(40001, "系统繁忙，请稍后重试");

        /* 数据错误：50001-599999 */
        messageMap.put(50001, "数据未找到");
        messageMap.put(50002, "数据有误");
        messageMap.put(50003, "数据已存在");
        messageMap.put(50004,"查询出错");

        /* 接口错误：60001-69999 */
        messageMap.put(60001, "内部系统接口调用异常");
        messageMap.put(60002, "外部系统接口调用异常");
        messageMap.put(60003, "该接口禁止访问");
        messageMap.put(60004, "接口地址无效");
        messageMap.put(60005, "接口请求超时");
        messageMap.put(60006, "接口负载过高");

        /* 权限错误：70001-79999 */
        messageMap.put(70001, "无权限访问");
    }
    public static ResponseData retParam(Integer status, Object data) {
        ResponseData responseData = new ResponseData(status, messageMap.get(status), data);
        return responseData;
    }
}