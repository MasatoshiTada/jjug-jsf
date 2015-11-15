package com.example.web.backingbean;

import com.example.persistence.entity.Employee;
import com.example.service.EmployeeService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
public class EmployeeIndexBean implements Serializable {
    
    @Inject
    private EmployeeService employeeService;
    
    private List<Employee> employeeList;

    @Pattern(regexp = "[a-zA-Z\\s]*", message = "{pattern.alphabet.or.space}")
    @Size(max = 10, message = "{size.string}")
    private String name;
    @Pattern(regexp = "[1-9][0-9]*", message = "{pattern.integer}")
    private String empId;
    
    @PostConstruct
    public void init() {
        employeeList = employeeService.findAll();
    }
    
    public void findByName() {
        name = name == null ? "" : name;
        employeeList = employeeService.findByName(name);
        
        if (employeeList.isEmpty()) {
            FacesMessage message = new FacesMessage("該当する社員は存在しませんでした");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public String throwRuntimeException() {
        throw new RuntimeException();
    }
    
    public String throwIOException() throws IOException {
        throw new IOException();
    }
    
    /**
     * @return the employeeList
     */
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    
}
