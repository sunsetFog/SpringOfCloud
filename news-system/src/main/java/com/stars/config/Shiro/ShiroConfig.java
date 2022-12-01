package com.stars.config.Shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/*
    Shiro谢咯：
    Shiro实现登录拦截，认证、授权、加密、会话管理、Web集成、缓存等
    1.Subject 用户
    2.SecurityManager 管理所有用户
    3.Realm 连接数据
    下载地址：https://shiro.apache.org/
    官方文档：https://shiro.apache.org/tutorial.html
*/
@Configuration
public class ShiroConfig {
    // Shiro过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroBean.setSecurityManager(defaultWebSecurityManager);
        /*
            添加shiro的内置过滤器，才能访问
            anon: 无需认证
            authc: 必须认证
            user: 必须有“记住我”功能用
            perms: 要拥有对某个资源的权限
            role: 要有某个角色权限


            拦截设置
        */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/add", "perms[user:add]");// 401  需要授权才不拦截
        // 授权，没有授权会跳到未授权页面
        filterMap.put("/user/update", "anon");
//        filterMap.put("/user/*", "authc"); // 这个认证会影响登录接口
        shiroBean.setFilterChainDefinitionMap(filterMap);
        /*
            设置登录的请求
            拦截的页面都跳登录页
        */
        shiroBean.setLoginUrl("/user/isLogin");
        /* 未授权的页面 */
        shiroBean.setUnauthorizedUrl("/user/noauth");

        System.out.println("--Shiro配置--");
        return shiroBean;
    }
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    // 创建realm对象
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
