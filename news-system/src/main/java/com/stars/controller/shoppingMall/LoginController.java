package com.stars.controller.shoppingMall;

import com.stars.aspect.annotation.AutoLog;
import com.stars.common.util.JwtTokenUtils;
import com.stars.common.fruit.ResponseData;
import com.stars.common.fruit.ResponseDataUtil;
import com.stars.common.util.oConvertUtils;
import com.stars.mapper.shoppingMall.UmsAdminMapper;
import com.stars.pojo.news.LoginParams;
import com.stars.pojo.shoppingMall.UmsAdmin;
import com.stars.service.BaseCommonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
自定义的UserRealm
*/
@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private BaseCommonService baseCommonService;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * study: 自定义注解
     * @param req
     * @param session
     * @return
     */
    @AutoLog(value = "登录成功666", logType = 1, operateType = 0)
    @ResponseBody
    @PostMapping("/user/login")
    public ResponseData userLogin(HttpServletRequest req, HttpSession session) {
        System.out.println("进来+++");
        String username = req.getParameter("username");
        System.out.println("username==="+username);
        String password = req.getParameter("password");
        String code = new String();
        String msg = new String();
        session.setAttribute("username", username);// 缓存
        System.out.println("--username--"+username);
        System.out.println("--password--"+password);

        // 获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject==="+subject);
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        System.out.println("--subject--"+subject);
        System.out.println("--token--"+token);

        try {
            // shiro登录，开始认证
            subject.login(token);
            code = "200";
            msg = "登录成功";
        } catch (UnknownAccountException e) {
            code = "0";
            msg = "用户名错误";
        } catch (IncorrectCredentialsException e) {
            code = "0";
            msg = "密码错误";
        } catch (AuthorizationException e) {
            code = "0";
            msg = "没有权限";
        }
        // 拿到User对象，认证return要传user进来
        LoginParams currentLoginParams = (LoginParams) subject.getPrincipal();
        System.out.println("--currentUser--"+ currentLoginParams);
        // 用户密码错误
        if(currentLoginParams == null) {
            return ResponseDataUtil.buildSuccess(code, msg);
        }

        String isToken = null;
        try {
            isToken = JwtTokenUtils.generTokenByRS256(currentLoginParams);

            LoginParams beLoginParams = JwtTokenUtils.verifierTokenBySysUser(isToken);
            System.out.println("--token校验--"+ beLoginParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String randomToken = UUID.randomUUID().toString();

        HashMap<String, String> Sites = new HashMap<>();
        Sites.put("token", isToken);

        System.out.println("--isToken--"+isToken);
        System.out.println("--随机token--"+randomToken);


        // redis存储token
        redisTemplate.opsForValue().set("access_token:", isToken, 1, TimeUnit.DAYS);

//        baseCommonService.addLog("用户名: " + username + ",登录成功！", 1, null);
        return ResponseDataUtil.buildSuccess(code, msg, Sites);
    }
    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/user/logout")
    public ResponseData logout(HttpServletRequest request, HttpServletResponse response) {
        // 获取header里的token
        String token = request.getHeader("token");
        // 没有token退出登录失败
        if(oConvertUtils.isEmpty(token)) {
            return ResponseDataUtil.buildError("退出登录失败！");
        }
        // 根据token获取Jwt存的对象
        LoginParams loginParams = JwtTokenUtils.accessObj(token);
        // 根据用户名查寻一条用户数据
        UmsAdmin umsAdmin = umsAdminMapper.selectRow(loginParams.getUsername());
        if(umsAdmin == null) {
            return ResponseDataUtil.buildError("Token无效!");
        }
        // 添加日志记录
        //清空用户登录Token缓存
        redisTemplate.delete("access_token");
        //调用shiro的logout
        SecurityUtils.getSubject().logout();

        return ResponseDataUtil.buildSuccess("退出登录成功！");
    }
}
