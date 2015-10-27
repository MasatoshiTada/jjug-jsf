package com.example.web.backingbean;

import com.example.service.EmployeeService;
import com.example.web.dto.EmployeeDto;
import com.example.web.util.FacesUtil;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

/**
 *
 * @author tada
 */
@Named
@ViewScoped
public class EmployeeDeleteBean implements Serializable {
    
    @Inject
    private EmployeeService employeeService;
    
    @Pattern(regexp = "[1-9][0-9]*", message = "{pattern.integer}")
    private String empId;
    
    private EmployeeDto employee;

    /**
     * delete.xhtmlの初期化時に呼ばれるイベントメソッドです。
     */
    public void preRenderView() {
        employee = employeeService.findByEmpId(Integer.valueOf(empId));
    }
    
    public String cancel() {
        return "index.xhtml" + FacesUtil.REDIRECT;
    }
    
    public String delete() {
        employeeService.delete(Integer.valueOf(empId));
        return "index.xhtml" + FacesUtil.REDIRECT;
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
     * @return the employeeDto
     */
    public EmployeeDto getEmployee() {
        return employee;
    }
    
}
