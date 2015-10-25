package com.example.web.backingbean;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import com.example.web.dto.DepartmentDto;
import com.example.web.dto.EmployeeDto;
import com.example.web.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    
    @Pattern(regexp = "[1-9][0-9]*")
    private String empId;
    @Size(min = 1, max = 40)
    @Pattern(regexp = "[a-zA-Z\\s]*")
    private String name;
    @NotNull
    private Date joinedDate;
    @NotNull
    @Pattern(regexp = "[1-9][0-9]*")
    private String deptId;
    private String deptName;
    private List<DepartmentDto> departmentList;
    
    public String update() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmpId(Integer.valueOf(empId));
        employeeDto.setName(name);
        employeeDto.setJoinedDate(joinedDate);
        
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDeptId(Integer.valueOf(deptId));
        employeeDto.setDepartment(departmentDto);
        
        employeeService.update(employeeDto);
        
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
     * init()メソッドより後に呼ばれます。
     */
    public void preRenderView() {
        if (conversation.isTransient()) {
            // 会話のタイムアウトを1分間に設定
            conversation.setTimeout(60000);
            // 会話を開始する
            conversation.begin();
            
            EmployeeDto employeeDto = employeeService.findByEmpId(Integer.valueOf(empId));
            empId = employeeDto.getEmpId().toString();
            name = employeeDto.getName();
            joinedDate = employeeDto.getJoinedDate();
            deptId = employeeDto.getDepartment().getDeptId().toString();
            deptName = employeeDto.getDepartment().getName();
            departmentList = departmentService.findAll();
        }
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
    public List<DepartmentDto> getDepartmentList() {
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
