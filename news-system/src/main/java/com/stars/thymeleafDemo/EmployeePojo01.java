package com.stars.thymeleafDemo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 员工表
@Data
//@AllArgsConstructor // 有参构造
@NoArgsConstructor // 无参构造
@ApiModel("用户实体类")
public class EmployeePojo01 {
    @JsonIgnore// jackson---排除Json序列化
    private Integer id;
    @ApiModelProperty("名字")
    private String lastName;
    @JsonProperty("askEmail")// 字段别名
    private String email;
    private Integer gender; // 0:女  1:男
    private DepartmentPojo01 departmentPojo01;// 部门类
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh")// jackson---日期格式化  yyyy-MM-dd hh:mm:ss
    @JsonInclude(JsonInclude.Include.NON_NULL)// jackson---null时，不包含这属性
    private Date birth;
    // 构造器
    public EmployeePojo01(Integer id, String lastName, String email, Integer gender, DepartmentPojo01 departmentPojo01) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.departmentPojo01 = departmentPojo01;
        this.birth = new Date(); // 默认创建日期
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public DepartmentPojo01 getDepartmentPojo01() {
        return departmentPojo01;
    }

    public void setDepartmentPojo01(DepartmentPojo01 departmentPojo01) {
        this.departmentPojo01 = departmentPojo01;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
