package com.n22;

import com.n22.config.MybatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootMybatisApplication {
	@Bean
	public Interceptor getInterceptor(){
		Interceptor interceptor=new MybatisInterceptor();
		System.out.println(interceptor);
		return interceptor;
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
}
