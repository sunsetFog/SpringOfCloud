package com.stars.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
// 异步任务
@Service
public class AsyncService {
    @Async // 注解是异步，需要程序入口类中开启异步才生效
    public void yibu() {
        try {
            Thread.sleep(3000);// 停止三秒
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println("数据正在处理....");
    }
}
