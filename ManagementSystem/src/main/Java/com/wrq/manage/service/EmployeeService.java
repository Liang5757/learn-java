package com.wrq.manage.service;

import com.wrq.manage.bean.Employee;
import com.wrq.manage.bean.EmployeeExample;
import com.wrq.manage.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iEmployeeService")
public class EmployeeService implements IEmployeeService {
    @Autowired // 自动装配
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有的员工信息
     * @return
     */
    public List<Employee> getAll(){
        return  employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 员工更新
     * @param employee
     */
    public void updateEmp(Employee employee){
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public void deleteEmp(Integer id){
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteBatch(List<Integer> ids){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }

    /**
     * 根据id查员工信息
     * @param id
     * @return
     */
    public Employee getEmp(Integer id){
        return employeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 员工添加
     * @param employee
     * @return result
     */
    public int addEmp(Employee employee){
        return employeeMapper.insertSelective(employee);
    }

    /**
     * 员工名是否可用
     * @param empName
     * @return true,代表当前姓名可用，false不可用
     */
    public boolean checkUser(String empName){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }
}
