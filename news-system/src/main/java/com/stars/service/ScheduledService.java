package com.stars.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

// 定时任务    在特定时间执行的任务   自动配置类：TaskExecutionAutoConfiguration
@Service
public class ScheduledService {
    /*
        @EnableScheduling 需要开启定时任务
        cron表达式
        搜索cron: https://www.bejson.com/othertools/cron/
        秒，分，时，日，月，周几
        每隔5分钟：0/5
        周一到周六：1-6
        10点和18点：10,18
        所有：?
    */
    @Scheduled(cron = "0 19 15 * * ?") // 每天的时分秒执行
    public void banana() {
        System.out.println("定时任务，你被执行了.");
    }

}
