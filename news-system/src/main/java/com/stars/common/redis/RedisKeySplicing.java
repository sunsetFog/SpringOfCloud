package com.stars.common.redis;

/**
 * redis 拼接 key 统一管理方法
 */
public class RedisKeySplicing {

    public static String getSmsCodeKey(String phone){
        return RedisCommonChar.SMS_CODE_PREFIX.concat(phone);
    }
    // 字符串拼接
    public static String getUserToken(String phone){
        return RedisCommonChar.USER_TOKEN.concat(phone);
    }

    public static String getPayLock(String userId){
        return RedisCommonChar.REDIS_LOCK.concat(userId);
    }

    public static String createRoom(String phone){
        return RedisCommonChar.REDIS_LOCK.concat(phone);
    }

    public static String requestLock(String method, String phone){
        return RedisCommonChar.REDIS_LOCK.concat(method).concat(":").concat(phone);
    }

}
