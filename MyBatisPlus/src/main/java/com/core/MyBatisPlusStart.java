package com.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.core.mapper")// 扫描mapper文件夹
@SpringBootApplication
public class MyBatisPlusStart {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusStart.class, args);
    }
}
