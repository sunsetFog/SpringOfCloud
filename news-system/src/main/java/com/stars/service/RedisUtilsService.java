package com.stars.service;
/*
    先写成interface接口
*/
public interface RedisUtilsService {

    boolean existsKey(String key);

    String getCacheStringInfo(String key);

    //timeOut : s
    void cacheStringInfo(String key, String value, long timeOut);

    //timeOut : day
    void cacheStringInfoByDay(String key, String value, long day);

    void removeByKey(String key);

}
