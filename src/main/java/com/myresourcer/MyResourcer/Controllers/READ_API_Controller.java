package com.myresourcer.MyResourcer.Controllers;

import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Services.READ_ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class READ_API_Controller {

    @Autowired
    private READ_ServiceManager serviceManager;


    public READ_API_Controller(){}


    ///GET REQUESTS --------------------------------------->

    @GetMapping("/requests")
    public List<Request> getAllRequests(){
        return serviceManager.getAllRequests();
    }

    @GetMapping("/assets")
    public List<Assets> getAllAssets(){
        return serviceManager.getAllAssets();
    }

    @GetMapping("/categories")
    public List<Categories> getAllCategories(){
        return serviceManager.getAllCategories();
    }

    @GetMapping("/conditions")
    public List<Condition> getAllConditions(){
        return serviceManager.getAllConditions();
    }

    @GetMapping("/departments")
    public List<Departments> getAllDepartments(){
        return serviceManager.getAllDepartments();
    }

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return serviceManager.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Roles> getAllRoles(){
        return serviceManager.getAllRoles();
    }

    @GetMapping("/statuses")
    public List<Status> getAllStatuses(){
        return serviceManager.getAllStatuses();
    }

    @GetMapping("/comments")
    public List<Comments> getAllComments(){
        return serviceManager.getAllComments();
    }

}
