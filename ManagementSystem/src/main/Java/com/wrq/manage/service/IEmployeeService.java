package com.wrq.manage.service;

import com.wrq.manage.bean.Employee;

import java.util.List;

public interface IEmployeeService {

    List<Employee> getAll(); // 获取所有员工列表

    int addEmp(Employee employee); // 添加员工

    boolean checkUser(String empName); // 检查用户名是否可用

    Employee getEmp(Integer id); // 获取员工信息

    void updateEmp(Employee employee); // 更新员工信息

    void deleteEmp(Integer id); // 删除员工

    void deleteBatch(List<Integer> ids); // 批量删除
}
