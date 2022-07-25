package com.core.config;

import feign.hystrix.FallbackFactory;

public class feignConfig implements FallbackFactory {
    @Override
    public String create(Throwable throwable){
        String water = "feign客户端降级的默认值";
        System.out.println(water);
        return water;
    }
}
