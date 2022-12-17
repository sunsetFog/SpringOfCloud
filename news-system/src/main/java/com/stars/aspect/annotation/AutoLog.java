package com.stars.aspect.annotation;

import java.lang.annotation.*;

/**
 * study: 自定义注解
 * 日志记录注解实体类
 */
/*
    用在哪里
    ElementType.*
        TYPE: 类，接口或者枚举
        FIELD: 域，包含枚举常量
        METHOD: 方法
        PARAMETER: 参数
        CONSTRUCTOR: 构造方法
        LOCAL_VARIABLE: 局部变量
        ANNOTATION_TYPE: 注解类型
        PACKAGE: 包
 */
@Target(ElementType.METHOD)
/*
    生存周期,保留到哪个阶段
    RetentionPolicy.*
        SOURCE: 源码级别保留，编译后即丢弃
        CLASS: 编译级别保留，编译后的class文件中存在，在jvm运行时丢弃，这是默认值
        RUNTIME: 运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用
 */
@Retention(RetentionPolicy.RUNTIME)
/*
    文档化
 */
@Documented
public @interface AutoLog {

    /**
     * 自定义注解日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 0:操作日志;1:登录日志;2:定时任务;
     */
    int logType() default 2;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除）
     */
    int operateType() default 0;

    /**
     * 模块类型 默认为common
     * @return
     */
//    ModuleType module() default ModuleType.COMMON;
}
