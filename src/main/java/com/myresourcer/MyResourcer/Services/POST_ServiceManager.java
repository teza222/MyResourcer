package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class POST_ServiceManager {

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



    public POST_ServiceManager() {
    }


    public boolean addRequest(DTO_Request request) {
        if (request != null) {
            Request formattedRequest = new Request();
            Assets assets = new Assets();
            assets.setAssetId(request.getAssetId());
            formattedRequest.setAssetId(assets);
            Users users = new Users();
            users.setId(request.getUserId());
            formattedRequest.setUserId(users);
            Status status = new Status();
            status.setStatusId(request.getStatusId());
            formattedRequest.setStatusId(status);
            Condition condition = new Condition();
            condition.setConditionId(request.getConditionId());
            formattedRequest.setConditionId(condition);
            formattedRequest.setDateOut(request.getDateOut());
            formattedRequest.setDateIn(request.getDateIn());
            formattedRequest.setTimeOut(request.getTimeOut());
            formattedRequest.setTimeIn(request.getTimeIn());
            requestRepository.save(formattedRequest);
            return true;
        } else {
            return false;
        }
    }


    public boolean addAsset(DTO_Assets assets) {
        if (assets != null) {
            Assets formmatedAssets = new Assets();

            formmatedAssets.setAssetId(assets.getAssetId());
            formmatedAssets.setItem(assets.getItem());
            formmatedAssets.setMobile(assets.isMobile());
            formmatedAssets.setSerialNumber(assets.getSerialNumber());
            formmatedAssets.setSpecifications(assets.getSpecifications());
            Categories categories = new Categories();
            categories.setCategoryId(assets.getCategoryId());
            formmatedAssets.setCategoryId(categories);

            assetRepository.save(formmatedAssets);
            return true;
        } else {
            return false;
        }
    }


    public boolean addCategory(Categories categoryData) {
        if (categoryData == null) {
            return false;
        }
        categoryRepository.save(categoryData);
        return true;
    }


    public boolean addCondition(Condition conditionData) {
        if (conditionData == null) {
            return false;
        }
        conditionRepository.save(conditionData);
        return true;
    }


    public boolean addDepartment(Departments departmentData) {
        if (departmentData == null) {
            return false;
        }
        departmentRepository.save(departmentData);
        return true;
    }


    public boolean addUser(Users users){
        if(users == null){
            return false;
        }
        userRepository.save(users);
        return true;
    }


    public boolean addRole(Roles roles){
        if(roles == null){
            return false;
        }
        roleRepository.save(roles);
        return true;
    }


    public boolean addStatus(Status status){
        if(status == null){
            return false;
        }
        statusRepository.save(status);
        return true;
    }


}
