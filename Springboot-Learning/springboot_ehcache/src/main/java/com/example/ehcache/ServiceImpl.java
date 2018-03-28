package com.example.ehcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/1/21.
 */
@Service
public class ServiceImpl {
    //这里的单引号不能少，否则会报错，被识别是一个对象;
    public static final String CACHE_KEY = "'toolkeys'";
    /**
     * value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
     */
    public static final String TOOL_CACHENAME = "tool";

    /**
     * 保存数据.
     * @param name
     */
    @CacheEvict(value=TOOL_CACHENAME,key=CACHE_KEY)
    public String save(String name){
        return name;
    }

    /**
     * 查询数据.
     * @param id
     * @return
     */
    @Cacheable(value=TOOL_CACHENAME,key="'toolkeys_'+#id")
    public String findById(Long id){
        System.err.println("没有走缓存！"+id);
        return id.toString();
    }

    /**
     * 在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     @CachePut也可以标注在类上和方法上。使用@CachePut时我们可以指定的属性跟@Cacheable是一样的。
     */
    @CachePut(value = TOOL_CACHENAME,key = "'toolkeys_'+#updated.getId()")
    public String update(String name) {
        return name;
    }


    /**
     * 删除数据.
     * @param id
     */
    @CacheEvict(value = TOOL_CACHENAME,key = "'toolkeys_'+#id")//清除缓存.
    public void delete(Long id){
    System.out.print("清除缓存");
    }

}
