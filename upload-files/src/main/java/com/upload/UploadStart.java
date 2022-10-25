package com.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@SpringBootApplication
public class UploadStart {
    public static void main(String[] args) {
        SpringApplication.run(UploadStart.class, args);
    }
}
