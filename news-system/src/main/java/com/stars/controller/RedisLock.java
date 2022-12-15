package com.stars.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * study: 高并发分布式锁
 * redis分布式锁
 * redisson高并发分布式锁:  https://redisson.org
 * 应用：互联网秒杀、抢优惠券
 * 下载RedisClient
 * Apache JMeter工具模拟高并发场景
 */
@RestController
@CrossOrigin
public class RedisLock {
    @Autowired
    private Redisson redisson;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/redis-lock")
    public String lockWay() {
        // 商品id---接口接收参数
        String goodsIdKey = "12";
        // 唯一value值----为了解决高并发锁永久失效问题
        String valueId = UUID.randomUUID().toString();
        // 单体架构，用synchronized没问题，既是打包由一个tomcat运行
        // 集群部署、分布式部署，既是打包由多个tomcat运行，高并发会重复减的bug
//        synchronized (this) {
//
//        }
        // 多线程执行，拿到锁则往下执行，否则阻塞不往下面执行，while循环判断线程执行完毕就加锁才能往下执行
        RLock lock = redisson.getLock(goodsIdKey);
        // 每隔10秒检查是否还持有锁，如果持有则延长锁30秒
        lock.lock(30, TimeUnit.SECONDS);
        // 报异常处理
        try {
            // redis语句：jedis.setnx(key,value)
            //Boolean flag = redisTemplate.opsForValue().setIfAbsent(goodsIdKey, valueId);/
            // 执行到这宕机，锁未释放，用链式写法
            // 考虑执行中间系统宕机，锁未释放，用10秒后删除   参数3：时间单位-秒
            //redisTemplate.expire(goodsIdKey, 10, TimeUnit.SECONDS);

            // 链式写法---问题高并发失效
//            Boolean flag = redisTemplate.opsForValue().setIfAbsent(goodsIdKey, valueId, 10, TimeUnit.SECONDS);
//            if (!flag) {
//                return "lock-error";
//            }
            // 主要内容。。。。
            // 获取redis库存数量
            int stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                // 库存数量减1
                int realStock = stock - 1;
                // redis设置库存数量
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存："+realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            // valueId 等于 redis里的value 才能删----为了解决高并发锁永久失效问题
//            if (valueId.equals(redisTemplate.opsForValue().get(goodsIdKey))) {
//                // 释放锁
//                redisTemplate.delete(goodsIdKey);
//            }
            // 释放锁
            lock.unlock();
        }
        return "success";
    }
}
