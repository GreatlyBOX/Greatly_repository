package com.n22.config;

/**
 * @Description
 * @Package com.jeesite.modules.sys.interceptor
 * @Author miaopeng
 * @Date 2019/06/26 14:45
 */

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })//按需配置
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("mybatis拦截器======");
        return invocation.proceed();
    }

    // 获取代理对象
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    // 设置代理对象的参数
    @Override
    public void setProperties(Properties properties) {
    }
}