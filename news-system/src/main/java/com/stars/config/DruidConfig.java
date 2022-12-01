package com.stars.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
/*
study: Druid
集成数据库
后台数据源监控网站
*/
@Configuration
public class DruidConfig {
    // 绑定yaml的数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }
    // 后台监控登录页配置,相当于web.xml
    @Bean
    public ServletRegistrationBean statViewServlet() {
        // 访问路径：http://localhost:****/druid
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 登录账户密码配置
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        // 增加配置
        objectObjectHashMap.put("loginUsername", "admin");
        objectObjectHashMap.put("loginPassword", "123456");
        // 允许谁可以访问
        objectObjectHashMap.put("allow", "");// 所以能访问
        // 禁止谁访问
//        objectObjectHashMap.put("小白", "192.168.11.123");

        bean.setInitParameters(objectObjectHashMap);// 设置初始化参数
        return bean;
    }

    // filter过滤器---yaml有了这可以删掉
//    @Bean
//    public FilterRegistrationBean webStatFilter() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new WebStatFilter());
//        // 可以过滤哪些请求,不统计
//        HashMap<Object, Object> initParameters = new HashMap<>();
//        initParameters.put("exclusions", "*.js,*.css,/druid/*");
//        bean.setInitParameters(initParameters);
//
//        return bean;
//    }
}
