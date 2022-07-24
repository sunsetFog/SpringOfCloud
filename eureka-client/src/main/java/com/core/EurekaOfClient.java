package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient // 开启Eureka客户端
@EnableFeignClients(basePackages = {"com.core"})// 扫面包
@EnableCircuitBreaker // 添加对熔断的支持
public class EurekaOfClient {
    public static void main(String[] args) {
        SpringApplication.run(EurekaOfClient.class, args);
    }
}
