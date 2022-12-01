package com.stars;

import com.stars.pojo.Goods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;



@SpringBootTest
public class redisTests {
    @Autowired // 写入Redis
//    @Qualifier("redisTemplate") // 和底层名字重复冲突，用这个解决
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println("--连接--"+jedis.ping());
        //  操作字符串
        System.out.println("--字符串--"+jedis.set("name", "bee02"));
        System.out.println("--字符串--"+jedis.get("name"));
        System.out.println("--字符串--"+jedis.keys("*"));
        // 操作List
        System.out.println("--List--"+jedis.lpush("list", "爱好"));
        System.out.println("--List--"+jedis.lrange("list", 0, -1));
        // 操作set
        System.out.println("--set--"+jedis.sadd("myset", "持续"));
        System.out.println("--set--"+jedis.smembers("myset"));


    }
    @Test
    void shiwu() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 开启事务
        Transaction multi = jedis.multi();
        try {
            multi.set("bee01", "vee01");
            // 执行事务
            multi.exec();
        } catch (Exception e) {
            // 放弃事务
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println("--蜜蜂--"+jedis.get("bee01"));
            // 关闭连接
            jedis.close();
        }
    }
    @Test
    void contextLoads3() {
		/*
			自动配置类 RedisAutoConfiguration
		 	redisTemplate操作不同的数据类型, 事务
		 	https://blog.csdn.net/qq_40580037/article/details/107508694
		 	Redis下载安装：https://github.com/microsoftarchive/redis/releases
		*/
        //  操作字符串
        Goods goods = new Goods();
        /*
            存对象时，要转字符串的对象和实现序列化
            方式1：ObjectMapper
            方式2：Goods类implements Serializable 存goods  属于JDK序列化，redis并不推荐
            方式3：Redis自定义模板   序列化配置    推荐
                方式1，2 属于String的序列化   方式3有String的序列化、json序列化
        */
        String s01 = null;
        try {
            s01 = new ObjectMapper().writeValueAsString(goods);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set("honey", goods);
        System.out.println("字符串= " + redisTemplate.opsForValue().get("honey"));
        // 操作List
		redisTemplate.opsForList().leftPush("list", "one");
        System.out.println("list= " + redisTemplate.opsForList().index("list", 0));
        System.out.println("list长度= " + redisTemplate.opsForList().size("list"));
        // 操作set
        redisTemplate.opsForSet().add("myset", "hello");
        System.out.println("set= " + redisTemplate.opsForSet().members("myset"));
        // 操作Hash
        redisTemplate.opsForHash().put("myhash", "key01", "value01");
        System.out.println("hash= " + redisTemplate.opsForHash().get("myhash", "key01"));
        // 操作Zset
        redisTemplate.opsForZSet().add("myzset", "one", 1);
        System.out.println("Zset= " + redisTemplate.opsForZSet().range("myzset", 0, -1));
        // 获取redis的连接对象
//		RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//		connection.flushDb();
//		connection.flushAll();
    }
}
