package com.stars.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.stars.aspect.annotation.AutoLog;
import com.stars.common.util.IpUtils;
import com.stars.common.util.ResponseData;
import com.stars.common.util.SpringContextUtils;
import com.stars.common.util.oConvertUtils;
import com.stars.enums.OperateTypeEnum;
import com.stars.pojo.news.LoginParams;
import com.stars.pojo.shoppingMall.LogDTO;
import com.stars.service.BaseCommonService;
import org.apache.poi.poifs.macros.Module;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * study: 自定义注解
 * 日志记录
 * 切面处理类
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 *
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，Around方法return退出时执行
 * @Before：前置增强，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @After: final增强，不管是抛出异常或者Around方法return退出都会执行
 *
 * 执行顺序：
 * 顺序4是执行自定义注解使用的方法体
 */
@Aspect// 把当前类标识为一个切面供容器读取
@Component
public class AutoLogAspect {

    @Resource
    private BaseCommonService baseCommonService;

    @Pointcut("@annotation(com.stars.aspect.annotation.AutoLog)")
    public void logPointCut() {
        System.out.println("1. Pointcut...");
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("2.环绕增强 around begin...");

        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time, result);

        System.out.println("5.环绕增强 around after....");

        return result;
    }
    @Before("logPointCut()")
    public void record(JoinPoint joinPoint) {
        System.out.println("3.前置增强 Before");
    }

    @After("logPointCut()")
    public void after() {
        System.out.println("6.final增强 After");
    }

    @AfterReturning("logPointCut()")
    public void AfterReturning() {
        System.out.println("7.AfterReturning Around方法return退出时执行");
    }

    @AfterThrowing("logPointCut()")
    public void AfterThrowing() {
        System.out.println("AfterThrowing 异常抛出增强");
    }

    // -------------------------------------

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object obj) {
        System.out.println("result=1="+obj);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 日志实体类---主要是填充值
        LogDTO dto = new LogDTO();
        // 注解实体类
        AutoLog syslog = method.getAnnotation(AutoLog.class);
        if(syslog != null){
            // 操作日志内容
            String content = syslog.value();
            // 模块判断
//            if(syslog.module()== ModuleType.ONLINE){
                content = getOnlineLogContent(obj, content);
//            }
            //注解上的描述
            dto.setLogType(syslog.logType());
            dto.setLogContent(content);
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        dto.setMethod(className + "." + methodName + "()");


        //设置操作类型
        if (2 == dto.getLogType()) {
            dto.setOperateType(getOperateType(methodName, syslog.operateType()));
        }

        //获取request
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        //请求的参数
        dto.setRequestParam(getReqestParams(request,joinPoint));
        //设置IP地址
        dto.setIp(IpUtils.getIpAddr());
        //获取登录用户信息
        LoginParams sysUser = (LoginParams) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null){
            dto.setUsername(sysUser.getUsername());
//            dto.setUserid(sysUser.getUsername());
//            dto.setUsername(sysUser.getRealname());

        }
        //耗时
        dto.setCostTime(time);
        dto.setCreateTime(new Date());
        //保存系统日志
        baseCommonService.addLog(dto);
    }


    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName,int operateType) {
        System.out.println("操作类型="+methodName);
        if (operateType > 0) {
            return operateType;
        }
        //update-begin---author:wangshuai ---date:20220331  for：阿里云代码扫描规范(不允许任何魔法值出现在代码中)------------
        return OperateTypeEnum.getTypeByMethodName(methodName);
        //update-end---author:wangshuai ---date:20220331  for：阿里云代码扫描规范(不允许任何魔法值出现在代码中)------------
    }

    /**
     * @Description: 获取请求参数
     * @author: scott
     * @date: 2020/4/16 0:10
     * @param request:  request
     * @param joinPoint:  joinPoint
     * @Return: java.lang.String
     */
    private String getReqestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            // java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //  https://my.oschina.net/mengzhang6/blog/2395893
            Object[] arguments  = new Object[paramsArray.length];
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindingResult || paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = paramsArray[i];
            }
            //update-begin-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
            PropertyFilter profilter = new PropertyFilter() {
                @Override
                public boolean apply(Object o, String name, Object value) {
                    int length = 500;
                    if(value!=null && value.toString().length()>length){
                        return false;
                    }
                    return true;
                }
            };
            params = JSONObject.toJSONString(arguments, profilter);
            //update-end-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        System.out.println("请求参数="+params);
        return params;
    }

    /**
     * online日志内容拼接
     * @param obj
     * @param content
     * @return
     */
    private String getOnlineLogContent(Object obj, String content){
        if (ResponseData.class.isInstance(obj)){
            ResponseData res = (ResponseData)obj;
            String msg = res.getMessage();
//            String tableName = res.getOnlTable();
//            if(oConvertUtils.isNotEmpty(tableName)){
//                content+=",表名:"+tableName;
//            }
//            if(res.isSuccess()){
                content+= ","+(oConvertUtils.isEmpty(msg)?"操作成功":msg);
//            }else{
//                content+= ","+(oConvertUtils.isEmpty(msg)?"操作失败":msg);
//            }
        }
        return content;
    }

}
