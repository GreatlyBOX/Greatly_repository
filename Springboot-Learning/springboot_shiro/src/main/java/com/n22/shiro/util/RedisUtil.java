package com.n22.shiro.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zwq on 2018-01-23.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;



@Component
public class RedisUtil {
    @SuppressWarnings("rawtypes")
    @Autowired
    private  RedisTemplate redisTemplate;

    /**
     * 生成唯一key方法
     * @param className 当前类的名称
     * @param id 对象主键id
     * @return
     */
    public  String getKey(String className,String id){
     String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return className+"-"+methodName+"-"+id;
    }

	/**
	 * @return
	 *
	 * @Title: setRedisValidKey
	 * @Description:  短信验证码的redis key -- 用于忘记密码
	 * @return String 返回类型
	 * @throws
	 */
	public String getRedisValidKey(String userCode) {
		return userCode = userCode+"-VerificationCode";
	}

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public  void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public  void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
    	boolean result = redisTemplate.hasKey(key);
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public  boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存并设置有效时间
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public  boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据查询id所有关联的key集合
     * @param id
     * @return
     */
    public  Set<String> getKeys(String id){
        Set<String> keys = redisTemplate.keys("*:" + id + "*");
        return  keys;
    }


    /**
     * 获取redis内存对象数量
     * @return
     */
    public  Integer getSize(){
        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

//    /**
//	 *
//	 * @Title: name
//	 * @Description: redis数据转MAP
//	 * @param @param object
//	 * @param @return    设定文件
//	 * @return Map<String,Object>    返回类型
//	 * @throws
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> name(Object object) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Object> resMap = new HashMap<String, Object>();
//		JSONObject jsonObject = JSONObject.fromObject(object);
//		map = jsonObject;
//		for (Entry<String, Object> entry : map.entrySet()) {
//			resMap.put(entry.getKey(), entry.getValue());
//			}
//		return resMap;
//
//	}
//
//	/**
//	 *
//	 * @Title: redisString
//	 * @Description: 解析redis数据
//	 * @param  object	redis对象数据
//	 * @param  objectKey	redis对象中的key
//	 * @param @return    设定文件
//	 * @return String    返回类型
//	 * @throws
//	 */
//	public String redisString(Object object,String objectKey) {
//		String result = "";
//		JSONObject jsonObject = JSONObject.fromObject(object);
//		result = jsonObject.get(objectKey).toString();
//		return result;
//
//	}




}