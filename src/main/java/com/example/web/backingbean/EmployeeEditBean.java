package com.example.web.backingbean;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import com.example.web.dto.DepartmentDto;
import com.example.web.dto.EmployeeDto;
import com.example.web.util.FacesUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author tada
 */
@Named
@ViewScoped
public class EmployeeEditBean implements Serializable {

    @Inject
    private EmployeeService employeeService;
    @Inject
    private DepartmentService departmentService;
    
    @Pattern(regexp = "[1-9][0-9]*")
    private String empId;
    @Size(min = 1, max = 40)
    private String name;
    private String joinedDate;
    private String deptId;
    
    
    private EmployeeDto employee;

    private List<DepartmentDto> departmentList;
    
    public String update() {
        employeeService.update(employee);
        return "index.xhtml" + FacesUtil.REDIRECT;
    }
    
    public void init() {
        employee = employeeService.findByEmpId(Integer.valueOf(empId));
        departmentList = departmentService.findAll();
    }
    
    /**
     * @return the empId
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     * @return the employee
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * @return the departmentList
     */
    public List<DepartmentDto> getDepartmentList() {
        return departmentList;
    }
}
