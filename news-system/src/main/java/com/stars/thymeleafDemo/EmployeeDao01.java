package com.stars.thymeleafDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 员工Dao
@Repository // 托管
public class EmployeeDao01 {
    // 模拟数据库中的数据
    private static Map<Integer, EmployeePojo01> newTable = null;
    // 员工所属的部门
    @Autowired // 注解
    private DepartmentDao01 departmentDao01;
     static {
         newTable = new HashMap<Integer, EmployeePojo01>(); // 创建一个员工表
         newTable.put(1001, new EmployeePojo01(1001, "小白", "1929288@qq.com", 0, new DepartmentPojo01(101, "A教学部")));
         newTable.put(1002, new EmployeePojo01(1002, "小兔", "2929288@qq.com", 0, new DepartmentPojo01(102, "B市场部")));
         newTable.put(1003, new EmployeePojo01(1003, "小花", "3929288@qq.com", 1, new DepartmentPojo01(103, "C教研部")));
         newTable.put(1004, new EmployeePojo01(1004, "小猫", "4929288@qq.com", 0, new DepartmentPojo01(104, "D运营部")));
         newTable.put(1005, new EmployeePojo01(1005, "小狗", "5929288@qq.com", 0, new DepartmentPojo01(105, "E后勤部")));
     }
    // 主键自增
    private static Integer initId = 1006;
    // 增加一个员工
    public void addEmployee(EmployeePojo01 employeePojo01) {
        // 缺id值
        if (employeePojo01.getId() == null) {
            employeePojo01.setId(initId++);
        }
        // 缺部门类
        employeePojo01.setDepartmentPojo01(
                departmentDao01.departmentsWay(
                    employeePojo01.getDepartmentPojo01().getId()// 已知部门id
                )
        );
        newTable.put(employeePojo01.getId(), employeePojo01);
    }
    // 查询全部员工信息
    public Collection<EmployeePojo01> allEmployee(){
        return newTable.values();
    }
    // 通过id查询员工
    public EmployeePojo01 rowEmployeeById(Integer id){
        return newTable.get(id);
    }
    //  通过id删除员工
    public void deleteEmployee(Integer id) {
        newTable.remove(id);
    }
}
