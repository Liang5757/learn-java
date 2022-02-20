package com.wrq.manage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wrq.manage.bean.Employee;
import com.wrq.manage.common.Msg;
import com.wrq.manage.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理增删改查
 */
@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService iEmployeeService;

    /**
     * 批量删除
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("ids") String ids) {
        //批量删除
        if (ids.contains("-")) {
            List<Integer> del_ids = new ArrayList<Integer>();
            String[] str_ids = ids.split("-");
            for (String string : str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            iEmployeeService.deleteBatch(del_ids);
        } else {
            Integer id = Integer.parseInt(ids);
            iEmployeeService.deleteEmp(id);
        }

        return Msg.success();
    }

    /**
     * 更新员工
     *
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        iEmployeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee emploee = iEmployeeService.getEmp(id);
        return Msg.success().add("emp", emploee);
    }

    /**
     * ajax请求方式
     * 导入jackson包
     *
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 5);
        List<Employee> employeeList = iEmployeeService.getAll();
        PageInfo pageInfo = new PageInfo(employeeList, 5);

        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @RequestMapping("/emp")
    @ResponseBody
    public Msg addEmp(@Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //校验失败返回错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                System.out.println("错误的字段名" + error.getField());
                System.out.println("错误的信息" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            int result = iEmployeeService.addEmp(employee);
            if (result != 0) {
                return this.getEmpsWithJson(1);
            } else {
                return Msg.fail().add("errMsg", "新增信息失败");
            }
        }
    }


    /**
     * 检查用户名是否可用
     *
     * @param empName
     * @return
     */
    @RequestMapping("/checkUser")
    @ResponseBody
    public Msg checkUser(@RequestParam("empName") String empName) {
        //合法性
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是2-5位中文6-16位数字或者字母");
        }
        boolean b = iEmployeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail().add("va_msg", "用户名已经存在");
        }
    }
}
