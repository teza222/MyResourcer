package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(nullable = false, unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Roles roleId;
    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Departments departmentId;
    private Integer flag;

    public Users(Integer id, String username, String password, String fname, String lname, String email, Roles roleId, Departments departmentId, Integer flag) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.roleId = roleId;
        this.departmentId = departmentId;
        this.flag = flag;
    }

    public Users(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public Departments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Departments departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}