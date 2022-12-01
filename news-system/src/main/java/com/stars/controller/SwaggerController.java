package com.stars.controller;

import com.stars.pojo.news.LoginParams;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
study: Swagger
访问界面：http://localhost:8067/sky/swagger-ui.html
*/
@RestController
@CrossOrigin
@Api(tags="模块-类名")// Swaggwer注解  Controller模块名
public class SwaggerController {
    // 返回值有实体类，就会扫描到Swagger里，显示在Models
    @ApiOperation("--看看就飒飒--")
    @PostMapping("/tongtong/bean")
    public LoginParams penBean() {
        return new LoginParams();
    }

    @GetMapping("/xiaoyue/apple")
    // @ApiOperation("--接口标题说明--")
    @ApiOperation(
            value = "根据用户 ID 和 用户名称获取用户信息"
            , notes = "测试"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    // 参数说明   或者用(@ApiParam("商品名222") 形参名)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods", value = "商品名", dataType = "String", paramType = "query", required = false)
    })
    // 是code值，不是http状态码
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功处理请求"),
            @ApiResponse(code = 401, message = "没有权限访问该服务"),
            @ApiResponse(code = 403, message = "权限不足无法访问该服务"),
            @ApiResponse(code = 404, message = "未发现该服务"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    // apple/用户名
//    public String apple(@ApiParam("用户名") String username) {
//        return "Hello " + username;
//    }
    public Map<String,Integer> apple(@RequestParam Integer id, @ApiParam("商品名222") @RequestParam String goodsName) {
        System.out.println("{ id : " + id + ", goodsName : " + goodsName + " }");
        Map<String,Integer>  myList =new HashMap<>();
        myList.put("鞋",400);
        myList.put("衬衫",300);
        myList.put("T恤",200);
        return myList;
    }
}
