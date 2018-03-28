    package com.example.ehcache;

    import org.springframework.cache.annotation.EnableCaching;
    import org.springframework.cache.ehcache.EhCacheCacheManager;
    import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.io.ClassPathResource;

    /**
     * Created by Administrator on 2018/1/21.
     */
    @Configuration
    @EnableCaching//标注启动缓存.
    public class EhCacheConfig {
        /**
         *  ehcache 主要的管理器
         * @param bean
         * @return
         */
        @Bean
        public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
            return new EhCacheCacheManager(bean.getObject());
        }

        /**
         * 加载配置文件
         * @return
         */
        @Bean
        public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
            EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
            cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
            cacheManagerFactoryBean.setShared(true);
            return cacheManagerFactoryBean;
        }
    }
