package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@SpringBootApplication
public class UploadStart {
    public static void main(String[] args) {
        SpringApplication.run(UploadStart.class, args);
    }
}
