package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GET_ServiceManager {

    @Autowired
    Request_Repository requestRepository;
    @Autowired
    Asset_Repository assetRepository;
    @Autowired
    Category_Repository categoryRepository;

    @Autowired
    Condition_Repository conditionRepository;

    @Autowired
    Department_Repository departmentRepository;

    @Autowired
    User_Repository userRepository;

    @Autowired
    Role_Repository roleRepository;

    @Autowired
    Status_Repository statusRepository;

    @Autowired
    Comment_Repository commentRepository;





    public GET_ServiceManager() {
    }


    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Assets> getAllAssets() {
        return assetRepository.findAll();
    }

    public Integer getAssetById(Integer id){
        Optional<Assets> asset = assetRepository.findById(id);
        if(asset.isPresent()){
            return asset.get().getAssetId();
        }
        return null;
    }


    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }



    public List<Condition> getAllConditions() {
        return conditionRepository.findAll();
    }


    public List<Departments> getAllDepartments() {
        return departmentRepository.findAll();
    }


    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    public List<Roles> getAllRoles(){
        return roleRepository.findAll();
    }


    public List<Status> getAllStatuses(){
        return statusRepository.findAll();
    }


    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }
}
