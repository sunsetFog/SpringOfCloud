package com.stars.config;

import com.stars.common.util.JsonUtils;
import com.stars.common.util.ResponseData;
import com.stars.service.RedisUtilsService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// http拦截器  在springMVC里实例化了
public class HttpInterceptor implements HandlerInterceptor {
    private RedisUtilsService redisUtilsService;
    // 构造器 WebMvcConfigurer里用了
    public HttpInterceptor(RedisUtilsService redisUtilsService){
        this.redisUtilsService = redisUtilsService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json");
//        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        //设置禁用缓存
//        response.setHeader("Cache-control","no-cache");

        /*
            springboot，shiro跨域CORS请求，拿不到headers中的token值解决
            postman测试接口正常，vue项目里就跨域了
            原来CROS复杂请求时会先发送一个OPTIONS请求，来测试服务器是否支持本次请求，这个请求时不带数据的，请求成功后才会发送真实的请求
        */
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));


        // 获取session缓存
        Object username = request.getSession().getAttribute("username");
        // 获取header里的token
        String token = request.getHeader("token");
        System.out.println("--http拦截器--" + token);

        // 下面是token校验
//        if(StringUtils.isEmpty(token)){
//            System.out.println("--111--");
//            penRenderJson(response, ResponseDataUtil.buildError("0", "请先登录"));
//            return false;// 拦截
//        }
//        try {
//            System.out.println("--222--");
//            LoginParams sysLoginParams = JWTUtils.verifierTokenBySysUser(token);
//            if(!redisUtilsService.existsKey(RedisKeySplicing.getUserToken(sysLoginParams.getUsername()))){
//                penRenderJson(response, ResponseDataUtil.buildError("0", "Token 失效请重新登录"));
//                return false;
//            }
//        } catch (SignatureVerificationException | AlgorithmMismatchException e) {
//            System.out.println("--333--");
//            penRenderJson(response, ResponseDataUtil.buildError("0", "Token 无效"));
//            return false;
//        } catch (TokenExpiredException e) {
//            System.out.println("--444--");
//            penRenderJson(response, ResponseDataUtil.buildError("0", "Token 失效请重新登录"));
//            return false;
//        } catch (Exception e) {
//            System.out.println("--555--");
//            penRenderJson(response, ResponseDataUtil.buildError("0", "请先登录！"));
//            return false;
//        }

        System.out.println("-通过-" + username);


        return true;// 不拦截
    }
    // 打印拦截错误信息
    private void penRenderJson(HttpServletResponse response, ResponseData responseData) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.obj2string(responseData));
    }
}
