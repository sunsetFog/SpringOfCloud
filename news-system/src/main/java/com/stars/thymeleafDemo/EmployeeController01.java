package com.stars.thymeleafDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/*
@RequestMapping  支持请求方式：get,post,put,head，delete，options, patch

Post获取form表单参数：
方法1.public String seasonAdd(MapEmployeePojo mapEmployeePojo) {
方法2.public String seasonAdd(Integer id, String lastName, String email, Integer gender, MapDepartmentPojo mapDepartmentPojo, Date birth) {

Post接收JSON数据
1.public String login(@RequestBody MapEmployeePojo mapEmployeePojo){
2.@RequestParam("username") String username

Get接收JSON数据
1.@RequestParam("username") String username
2.HttpServletRequest request
String username = request.getParameter("username");

Get接收path参数
@GetMapping("store/update/{id}")
@PathVariable("id") int id

在GET请求中，不能使用@RequestBody。
在POST请求，可以使用@RequestBody和@RequestParam， 注意：分别对应前端传参方式的data和params

这个是前后端不分离开发  独立化工程
可用ui框架 bootstrap layui semantic-ui
*/
@Controller
public class EmployeeController01 {
    @Autowired
    EmployeeDao01 employeeDao01;
    @Autowired
    DepartmentDao01 departmentDao01;

    @RequestMapping("/season/list")
    public String seasonList(Model model){
        // 获取所有员工
        Collection<EmployeePojo01> all = employeeDao01.allEmployee();
        model.addAttribute("employeeList", all);
        return "employee/list.html";

    }
    // 跳转添加页
    @GetMapping("/season/toAdd")
    public String seasonToAdd(Model model) {
        // 获取所有部门
        Collection<DepartmentPojo01> departmentList = departmentDao01.departmentsWay();
        model.addAttribute("departmentList", departmentList);
        return "employee/add";
    }
    // 用于添加   输入日期必须是yyyy-MM-dd格式，否则报400
    @PostMapping("/season/add")
    public String seasonAdd(EmployeePojo01 employeePojo01, Model model) {
        System.out.println("--保存功能--：" + employeePojo01);
        employeeDao01.addEmployee(employeePojo01);
        seasonList(model);
        return "employee/list";
    }
    // 跳转修改页
    @GetMapping("/season/toUpdate/{id}")
    public String seasonToUpdate(@PathVariable("id")Integer id, Model model) {
        // 获取id的那条数据
        EmployeePojo01 employeePojo01ById = employeeDao01.rowEmployeeById(id);
        model.addAttribute("tiao", employeePojo01ById);
        seasonToAdd(model);
        return "employee/update";
    }
    // 修改
    @RequestMapping("/season/update")
    public String seasonUpdate(EmployeePojo01 employeePojo01, Model model) {
        System.out.println("修改功能：" + employeePojo01);
        employeeDao01.addEmployee(employeePojo01);
        seasonList(model);
        return "employee/list";
    }
    // 删除
    @GetMapping("/season/delete/{id}")
    public String seasonDelete(@PathVariable("id")Integer id, Model model) {
        employeeDao01.deleteEmployee(id);
        seasonList(model);
        return "employee/list";
    }
}
