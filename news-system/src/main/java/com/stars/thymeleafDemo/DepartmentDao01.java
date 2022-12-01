package com.stars.thymeleafDemo;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 部门dao
@Repository // 托管
public class DepartmentDao01 {
    // 模拟数据库中的数据
    private static Map<Integer, DepartmentPojo01> newTable = null;
     static {
         newTable = new HashMap<Integer, DepartmentPojo01>(); // 创建一个部门表
         newTable.put(101, new DepartmentPojo01(101, "W教学部"));
         newTable.put(102, new DepartmentPojo01(102, "W市场部"));
         newTable.put(103, new DepartmentPojo01(103, "E教研部"));
         newTable.put(104, new DepartmentPojo01(104, "E运营部"));
         newTable.put(105, new DepartmentPojo01(105, "E后勤部"));
     }
    // 获得所有部门数据
    public Collection<DepartmentPojo01> departmentsWay() {
        return newTable.values();
    }
    // 通过id得到部门
    public DepartmentPojo01 departmentsWay(Integer id) {
        return newTable.get(id);
    }
}
