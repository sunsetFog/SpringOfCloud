package com.stars.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * study: EasyExcel导出导入
 */
@Data
public class ExcelStudent {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("学号")
    private String number;
    public ExcelStudent(String name, String number) {
        this.name = name;
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ExcelStudent{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
