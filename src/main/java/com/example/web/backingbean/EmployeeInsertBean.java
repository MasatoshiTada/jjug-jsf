package com.example.web.backingbean;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import com.example.web.dto.DepartmentDto;
import com.example.web.dto.EmployeeDto;
import com.example.web.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author tada
 */
@Named
@ConversationScoped
public class EmployeeInsertBean implements Serializable {
    
    @Inject
    private EmployeeService employeeService;
    @Inject
    private DepartmentService departmentService;
    @Inject
    private Conversation conversation;
    
    @Size(min = 1, max = 40)
//    @Pattern(regexp = "[a-ZA-Z]*")
    private String name;
    @NotNull
    private Date joinedDate;
    @Pattern(regexp = "[1-9][0-9]*")
    private String deptId;
    private String deptName;
    
    private List<DepartmentDto> departmentList;
    
    @PostConstruct
    public void init() {
        if (conversation.isTransient()) {
            conversation.setTimeout(60000);
            conversation.begin();
            departmentList = departmentService.findAll();
        }
    }
    
    public String confirm() {
        deptName = departmentService.findById(Integer.valueOf(deptId)).getName();
        return "insert-confirm.xhtml";
    }
    
    public String cancel() {
        deptName = departmentService.findById(Integer.valueOf(deptId)).getName();
        return "insert.xhtml";
    }
    
    public String insert() {
        EmployeeDto employee = new EmployeeDto();
        employee.setName(name);
        employee.setJoinedDate(joinedDate);
        DepartmentDto department = new DepartmentDto();
        department.setDeptId(Integer.valueOf(deptId));
        department.setName(deptName);
        employee.setDepartment(department);
        
        System.out.println("============ deptId : " + deptId);
        System.out.println("============ IN BEAN " + employee);
        employeeService.insert(employee);
        
        conversation.end();
        
        return "index.xhtml" + FacesUtil.REDIRECT;
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
     * @return the joinedDate
     */
    public Date getJoinedDate() {
        return joinedDate;
    }

    /**
     * @param joinedDate the joinedDate to set
     */
    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    /**
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @return the departmentList
     */
    public List<DepartmentDto> getDepartmentList() {
        return departmentList;
    }
    
    
}
