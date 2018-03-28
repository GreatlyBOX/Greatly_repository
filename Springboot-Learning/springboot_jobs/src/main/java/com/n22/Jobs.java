package com.n22;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Mr on 2017/12/26.
 * 定时任务
 * @Configuration 将类托管给Spring  等价于<bean\>
 * @EnableScheduling 开启定时任务
 */
@Configuration
@EnableScheduling
public class Jobs {
    @Scheduled(cron = "0/1 * * * * ?") // 每20秒执行一次
    public void test(){
        System.out.println("执行定时任务");
    }
}
