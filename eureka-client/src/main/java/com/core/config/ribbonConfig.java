package com.core.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/*
    配置负载均衡实现
    Ribbon接口域名用: http://localhost:8001/ 变 http://eureka-client/   用RestTemplate调用接口
*/
@Configuration
public class ribbonConfig {
    @Bean
    @LoadBalanced // Ribbon
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
