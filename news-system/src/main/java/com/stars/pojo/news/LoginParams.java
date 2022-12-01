package com.stars.pojo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * study: Swagger
 */
@Data
@NoArgsConstructor // 无参构造
@AllArgsConstructor // 有参构造
@ApiModel("用户实体类") // Swaggwer注解   Model模块名
public class LoginParams implements Serializable {// 实体类序列化
    private int id;
    @ApiModelProperty("用户名") // Swaggwer注解
    private String username;
    @ApiModelProperty("密码") // Swaggwer注解
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
