package com.swagger.demo.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;


/**
 * @author 启哲
 * @创建时间 2020年7月27日
 * @知乎专栏 Spring Boot学习教程
 * https://zhuanlan.zhihu.com/c_152148543
 */
@Configuration
public class SwaggerConfig {

    // 定义分隔符
    private static final String separation_character = ";";


    /**
     * 创建API应用
     * api() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     *
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.swagger.demo.test1"+separation_character+"com.swagger.demo.test2"))
//                .apis(basePackage("com.swagger.demo.test1"))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * API文档的描述
     *
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("测试模块")
                //创建人
                .contact(new Contact("启哲", "", "xxxxxx@qq.com"))
                //版本号
                .version("v1")
                //描述
                .description("测试模块")
                .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(separation_character)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
