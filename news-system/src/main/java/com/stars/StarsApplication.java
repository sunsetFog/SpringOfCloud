package com.stars;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
	启动类
	Spring官方文档（依赖POM配置） https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#using-boot-starter
	框架发展史：
		javaWeb：前后不分离，MVC三层架构
		ssm框架：简化了开发流程，配置也开始较为复杂
		springboot微服务架构 内嵌Tomcat运行服务器
		springcloud服务越来越多就发展有了
*/
@EnableAsync // 开启异步注解功能
@EnableScheduling // 开启定时任务
// 程序主入口
@SpringBootApplication
//@MapperScan("com.stars.mapper")// mybatis的扫描包，@MapperScan是全局扫描，局部扫描@Mapper
public class StarsApplication {
	// springboot应用启动
	public static void main(String[] args) {
		SpringApplication.run(StarsApplication.class, args);
	}
	// study: 高并发分布式锁    redisson客户端连接
	@Bean
	public Redisson redisson() {
		Config config = new Config();
		// 单机模式
		config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("root").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}

}
