package com.stars.pojo.shoppingMall;

import lombok.Data;

import java.util.Date;

/**
 * study: 日志
 * 日志对象
 */
@Data
public class LogDTO {

    private static final long serialVersionUID = 8482720462943906924L;

    /**内容*/
    private String logContent;

    /**日志类型(0:操作日志;1:登录日志;2:定时任务)  */
    private Integer logType;

    /**操作类型(1:添加;2:修改;3:删除;) */
    private Integer operateType;

    private String id;
    private String createBy;
    private Date createTime;
    private Long costTime;
    private String ip;

    /**请求参数 */
    private String requestParam;

    /**请求类型*/
    private String requestType;

    /**请求路径*/
    private String requestUrl;

    /**请求方法 */
    private String method;

    /**操作人用户名称*/
    private String username;

    /**操作人用户账户*/
    private String userid;

    public LogDTO(){

    }

    public LogDTO(String logContent, Integer logType, Integer operatetype){
        this.logContent = logContent;
        this.logType = logType;
        this.operateType = operatetype;
    }
}
