package com.myresourcer.MyResourcer.DTOs;

public class DTO_Users {
    private Integer id;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private Integer roleId;
    private Integer departmentId;
    private Integer flag;

    public DTO_Users() {
    }

    public DTO_Users(Integer id, String username, String password, String fname, String lname, String email, Integer roleId, Integer departmentId, Integer flag) {
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
