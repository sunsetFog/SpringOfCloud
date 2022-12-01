package com.stars.controller;

import com.stars.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
study: lombok日志@Slf4j
日志级别：
默认是info，那么trace < debug更低级的不会显示
trace < debug < info < warn < error < fatal
跟踪 < 调试 < 信息 < 警告 < 异常 < 危险
*/
@Slf4j// 方便
@RestController
@CrossOrigin
public class LombokOfSlf4j {
    // slf4j定义日志变量---要引入，不方便
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/journal")
    public String toJournal() {
        logger.info("四甲基");
        log.info("单精度季度奖金");
        log.info("占位日志--[{}]--", "可考虑");
        log.debug("开始校验[操作权限]");
        // 处理日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date isDate = new Date();
        try {
            isDate = dateFormat.parse("2YYU");
        }catch (Exception e){
//            e.printStackTrace();
            log.info("---123----"+e.getMessage());
        }
        ApiException.fail("该优惠券不可用");
        return "Are you OK?";
    }

    @GetMapping("/Jul")
    public String toJul() {
        // Jul日志
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LombokOfSlf4j.class.getName());
        logger.info("崇尚官方开发组：Jul日志");

        /*
            log4j日志略，不维护了的，要配置文件才能用，淘汰了
            org.apache.logging.log4j.Logger;
        */
        return "Are you OK?";
    }

}
