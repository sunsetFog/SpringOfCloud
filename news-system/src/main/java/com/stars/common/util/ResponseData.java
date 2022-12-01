package com.stars.common.util;

import java.io.Serializable;

/*
    util工具类
    Springboot三部曲之ResponseData<T>   https://blog.csdn.net/weixin_33863087/article/details/89540234

    一、接口返回的数据结构
*/
public class ResponseData<T> implements Serializable {// 泛型类 + 序列化接口

    private String code;

    private String message;
    // 泛型变量
    private T data;

    // 默认构造器---用不到，可删
    public ResponseData() {
    }

    // 构造器里添加泛型变量值---type1---三个参数
    public ResponseData(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // 构造器里添加泛型变量值---type2---两个参数
    public ResponseData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
