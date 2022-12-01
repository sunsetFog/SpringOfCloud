package com.stars.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
/*
按钮切换语言配置
*/
public class Lang8n implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 请求获取参数
        String lang = request.getParameter("lang");

        // 获取默认
        Locale locale = Locale.getDefault();
        /*
            判断是否为空
            判断是否相等：  && "123456".equals(password)
        */
        if (!StringUtils.isEmpty(lang)) {
            // zh_CN
            String[] split = lang.split("_");
            locale = new Locale(split[0], split[1]);
        }
        System.out.println("国际多语言："+ lang + "---默认语言："+ locale);

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
