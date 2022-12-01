package com.stars.thymeleafDemo;

import lombok.Data;
import lombok.NoArgsConstructor;

// 部门表
@Data
//@AllArgsConstructor // 有参
@NoArgsConstructor // 无参    不加则报错 java.lang.NoSuchMethodException: com.stars.pojo.DepartmentPojo01.<init>()
public class DepartmentPojo01 {
    private Integer id;
    private String departmentName;
    // 写了有参构造，重载了构造函数，而Mybatis在load进一个bean类时，需要无参构造
    public DepartmentPojo01(Integer id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
