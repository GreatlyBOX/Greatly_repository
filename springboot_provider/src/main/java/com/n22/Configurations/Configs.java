package com.n22.Configurations;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/1/9.
 * 配置类
 */
@Configuration
public class Configs {
    /**
     * 分页配置
     * @return
     */
    @Bean
        public PageHelper pageHelper() {
            PageHelper pageHelper = new PageHelper();
            Properties p = new Properties();
            //页码和页面大小两个参数进行分页
            p.setProperty("offsetAsPageNum", "true");
            //设置为true时，使用RowBounds分页会进行count查询
            p.setProperty("rowBoundsWithCount", "true");
            //3.5.0版本可用 - 为了支持startPage(Object params
            p.setProperty("reasonable", "true");
            pageHelper.setProperties(p);
            return pageHelper;
        }
    /**
     * FastJson返回
     * @param
     */
    @Bean
    public HttpMessageConverters configureMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //是否格式化返回Json
                SerializerFeature.PrettyFormat
        );
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return  new HttpMessageConverters(fastConverter);
    }
    }
