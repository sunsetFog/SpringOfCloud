package com.stars.service.impl;


import com.stars.service.RedisUtilsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtilsServiceImpl implements RedisUtilsService {

    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;
    //
    @Override
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }
    // 获取redis存储的token
    @Override
    public String getCacheStringInfo(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        return object != null ? object.toString() : null;
    }
    // redis存储token
    @Override
    public void cacheStringInfo(String key, String value, long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }
    // redis存储token
    @Override
    public void cacheStringInfoByDay(String key, String value, long day) {
        redisTemplate.opsForValue().set(key, value, day, TimeUnit.DAYS);
    }
    // redis删除存储的token
    @Override
    public void removeByKey(String key) {
        redisTemplate.delete(key);
    }

}
