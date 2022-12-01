package com.stars.common.redis;

public class RedisCommonChar {

    /**
     * 短信注册 key
     */
    public static final String SMS_CODE_PREFIX = "SMS_CODE:";

    /**
     * 存储用户 token
     */
    public static final String USER_TOKEN = "USER_TOKEN:";

    /**
     * Redis lock
     */
    public static final String REDIS_LOCK = "REDIS_LOCK:";

    /**
     * 方法请求锁 KEY + userId + method
     */
    public static final String REQUEST_LIMIT_LOCK = "REQUEST_LIMIT_LOCK:";

    /**
     * 用户输入密码超过限制会冻结用户
     */
    public static final String USER_INPUT_PWD = "USER_INPUT_PWD:";
}
