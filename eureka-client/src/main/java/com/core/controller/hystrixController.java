package com.core.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hystrixController {
    @GetMapping("/hystrix/list")
    @HystrixCommand(fallbackMethod = "hystrixGet") // 不是全局写法
    public String hystrixList() {
        // sql语句
        String res = null;
        if (res == null) {
            /*
                抛异常，hystrixGet方法触发
                throw有 return 类型;  的作用
            */
            throw new RuntimeException("没有该id");
        }
        return "成功！";
    }
    public String hystrixGet() {
        return "没有对应信息";
    }
    @GetMapping("/hystrix/id")
    public String hystrixWay() {
        String res = null;
        if (res == null) {
            throw new RuntimeException("没有该id");
        }
        return "成功！";
    }
}
