package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
public class Departments {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer departmentId;
    @Column(nullable = false)
    private String departmentName;

    public Departments(Integer departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Departments() {
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
