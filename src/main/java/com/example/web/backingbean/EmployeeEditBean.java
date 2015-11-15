package com.example.web.backingbean;

import com.example.persistence.entity.Employee;
import com.example.persistence.entity.Department;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
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
public class EmployeeEditBean implements Serializable {

    @Inject
    private EmployeeService employeeService;
    @Inject
    private DepartmentService departmentService;
    @Inject
    private Conversation conversation;
    
    @Pattern(regexp = "[1-9][0-9]*", message = "{pattern.integer}")
    private String empId;
    @NotNull(message = "{notnull}")
    @Size(min = 1, max = 40, message = "{size.string}")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "{pattern.alphabet.or.space}")
    private String name;
    @NotNull(message = "{notnull}")
    private Date joinedDate;
    @NotNull(message = "{notnull}")
    @Pattern(regexp = "[1-9][0-9]*", message = "{pattern.integer}")
    private String deptId;
    private String deptName;
    private List<Department> departmentList;
    
    public String update() {
        Employee employee = new Employee();
        employee.setEmpId(Integer.valueOf(empId));
        employee.setName(name);
        employee.setJoinedDate(joinedDate);
        
        Department department = new Department();
        department.setDeptId(Integer.valueOf(deptId));
        employee.setDepartment(department);
        
        employeeService.update(employee);
        
        conversation.end();
        
        return "index.xhtml" + FacesUtil.REDIRECT;
    }
    
    public String confirm() {
        deptName = departmentService.findById(Integer.valueOf(deptId)).getName();
        return "edit-confirm.xhtml";
    }

    public String cancel() {
        return "edit.xhtml";
    }
    
    /**
     * edit.xhtmlの初期化時にイベントで呼ばれるメソッドです。
     * [at]PostConstructが付加されたメソッドより後に呼ばれます。
     */
    public void preRenderView() {
        System.out.println("preRenderView");
        if (conversation.isTransient()) {
            // 会話のタイムアウトを1分間に設定
            conversation.setTimeout(60000);
            // 会話を開始する
            conversation.begin();
            Employee employee = employeeService.findByEmpId(Integer.valueOf(empId)).get();
            empId = employee.getEmpId().toString();
            name = employee.getName();
            joinedDate = employee.getJoinedDate();

            Department department = employee.getDepartment();
            deptId = department.getDeptId().toString();
            deptName = department.getName();

            departmentList = departmentService.findAll();
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("postConstruct");
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
     * @return the departmentList
     */
    public List<Department> getDepartmentList() {
        return departmentList;
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
    
    // setDeptName()は、ビューから呼ばれることが無いので作っていません
}
