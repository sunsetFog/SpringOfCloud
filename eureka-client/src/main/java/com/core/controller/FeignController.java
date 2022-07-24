package com.core.controller;

import com.core.mapper.FeignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    private FeignMapper feignMapper;

    @GetMapping("/feign/list")
    public String feignList() {
//        feignMapper.selectWay();
        return "feign_list";
    }
}
