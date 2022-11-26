package com.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Value("${rafael.file.path}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
        MVC配置使http://IP:端口号/${server.context-path}/profile/图片路径可以访问到配置的文件夹
        http://localhost:8060/sky/profile/goodsImportExcelErrorLog4590.txt

        addResourceHandler加一个路径校验，路径必须要有profile
        下载txt https://blog.csdn.net/qq_44717657/article/details/125255185
         */
        registry.addResourceHandler("/profile/**").addResourceLocations("file:"+basePath);

    }
}
