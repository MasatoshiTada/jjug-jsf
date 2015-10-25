package com.example.web.backingbean;

import com.example.service.EmployeeService;
import com.example.web.dto.EmployeeDto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
public class EmployeeIndexBean implements Serializable {
    
    @Inject
    private EmployeeService employeeService;
    
    private List<EmployeeDto> employeeList;

    @Pattern(regexp = "[a-zA-Z]*")
    private String name;
    
    @PostConstruct
    public void init() {
        employeeList = employeeService.findByName("");
    }
    
    public void findByName() {
        employeeList = employeeService.findByName(name);
        
        if (employeeList.isEmpty()) {
            FacesMessage message = new FacesMessage("該当する社員は存在しませんでした");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    /**
     * @return the employeeList
     */
    public List<EmployeeDto> getEmployeeList() {
        return employeeList;
    }

    /**
     * @param employeeList the employeeList to set
     */
    public void setEmployeeList(List<EmployeeDto> employeeList) {
        this.employeeList = employeeList;
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
    
}
