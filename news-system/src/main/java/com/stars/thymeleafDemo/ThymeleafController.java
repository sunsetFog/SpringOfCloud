package com.stars.thymeleafDemo;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.pojo.news.LoginParams;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
/*
引入Thymeleaf模板引擎
静态资源可放置和优先级（顺序排了）：classpath:/resources/   classpath:/static/   classpath:/public/
默认访问静态资源的首页html：http://localhost:8062/sky/
访问文件：http://localhost:8062/sky/1.js
模板引擎-Thymeleaf官网： https://www.thymeleaf.org/
Thymeleaf在Github主页：https://github.com/thymeleaf/thymeleaf
webjars官网  https://www.webjars.org/
访问jquery  http://localhost:8062/sky/webjars/jquery/3.4.1/jquery.js
*/
@Controller// 跳html文件去
//@RestController // 为了返回字符串
public class ThymeleafController {
    // 简单接口：http://localhost:8080/hello
    @GetMapping("/hello")
    // 调用业务，接收前端参数
    public String hello() {
        return "Hello World!!!";
    }

    /*
        访问使用模板引擎的html文件    html放置classpath:/templates
        优先级：Controller的html跳转  >  SpringMVC视图默认跳转
        路由视图的两种方式：
        1.MyMvcConfig的路由视图
        2.@RequestMapping({"/", "/water.html"})
    */
    @GetMapping({"/water", "/water.html"})
    public String water(Model model) {
        model.addAttribute("msg", "model信息<span>识别标签</span>");
        model.addAttribute("users", Arrays.asList("红红", "晴晴"));
        return "water";// 跳转的页面    二层写法：employee/list
    }

    @GetMapping("/user/login")
    public String toBeLogin(Model model) {
        model.addAttribute("message", "请输入用户名与密码");
        return "/user/login";
    }
    @GetMapping("/user/add")
    public String toBeAdd() {
        return "/user/add";
    }
    @GetMapping("/user/update")
    public String toBeUpdate() {
        return "/user/update";
    }
    @GetMapping("/user/noauth")
    public String toBeNoauth() {
        return "/user/noauth";
    }

    /*
        RestTemplate调用第三方接口
    */
    private final RestTemplate restTemplate;
    public ThymeleafController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    @GetMapping("/toOrder1")
    public ResponseData toOrder1() {
        LoginParams loginParams = new LoginParams();
        loginParams.setUsername("rafael");
        loginParams.setPassword("123456");
        System.out.println("--user000-"+ loginParams);
        ResponseData forObject = restTemplate.getForObject("http://localhost:8062/sky/toApple", ResponseData.class, loginParams);
        return forObject;
    }
    @GetMapping("/toApple")
    public ResponseData toApple(HttpServletRequest request) {
        String user = request.getParameter("user");
        System.out.println("--user111-"+user);
        return ResponseDataUtil.buildSuccess("200", "画好后", user);
    }

    @GetMapping("/toOrder2")
    public ResponseData toOrder2() {
        LoginParams loginParams = new LoginParams();
        loginParams.setUsername("rafael");
        loginParams.setPassword("123456");
        System.out.println("--user000-"+ loginParams);
        /*
            getForObject：
            参数1：url
            参数2：接口返回类型
            参数3：传参

            postForObject：
            参数1：url
            参数2：传参
            参数3：接口返回类型
         */
        ResponseEntity<ResponseData> responseDataResponseEntity = restTemplate.postForEntity("http://localhost:8062/sky/toApple", loginParams, ResponseData.class);
        return responseDataResponseEntity.getBody();
    }
    @PostMapping("/toApple")
    public ResponseData toApple2(@RequestBody LoginParams loginParams) {
        System.out.println("--user222-"+ loginParams);
        return ResponseDataUtil.buildSuccess("200", "画好后", loginParams);
    }
}
