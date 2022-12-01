package com.stars.controller;

import com.stars.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
任务调度：
    异步任务
*/
@RestController
public class AsyncController {
    @Autowired
    AsyncService asyncService;

    @GetMapping("/yibu")
    public String yibu() {
        asyncService.yibu();// 停止三秒，转圈···
        return "OK";// 等三秒才返回   优化：用异步秒返回，异步任务排最后执行
    }
}
