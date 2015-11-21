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
// TODO: 演習1-1. 名前付けアノテーションとビュースコープアノテーションを追加


public class EmployeeIndexBean implements Serializable {
    
    @Inject
    private EmployeeService employeeService;
    
    /**
     * 社員リスト
     */
    private List<Employee> employeeList;

    // TODO: 演習2-1. 検証アノテーションを追加する
    // 検証ルール1 正規表現で、"[a-zA-Z\\s]*"を指定。メッセージは"{pattern.alphabet.or.space}"
    // 検証ルール2 文字列長の最大値を10に指定。メッセージは"{size.string}"
    
    private String name;
    
    @PostConstruct
    public void init() {
        employeeList = employeeService.findAll();
    }
    
    /**
     * 名前検索メソッド
     */
    public void findByName() {
        name = name == null ? "" : name;
        
        // TODO: 演習1-2. employeeServiceのfindByName()メソッドを呼び出してemployeeListに代入する。引数はname。
        employeeList = null;
        
        if (employeeList.isEmpty()) {
            FacesMessage message = new FacesMessage("該当する社員は存在しませんでした");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // ignore
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

}
