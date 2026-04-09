package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Comments;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.DTOs.DTO_Users;
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

    @Autowired
    Comment_Repository commentRepository;



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
            formmatedAssets.setMobile(assets.getMobile());
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


    public boolean addUser(DTO_Users users){
        if(users == null){
            return false;
        }
        Users newUsers = new Users();
        newUsers.setUsername(users.getUsername());
        newUsers.setPassword(users.getPassword());
        newUsers.setFname(users.getFname());
        newUsers.setLname(users.getLname());
        Roles roles = new Roles();
        roles.setRoleId(users.getRoleId());
        newUsers.setRoleId(roles);
        Departments departments = new Departments();
        departments.setDepartmentId(users.getDepartmentId());
        newUsers.setDepartmentId(departments);
        newUsers.setFlag(users.getFlag());
        userRepository.save(newUsers);
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


    public boolean addComment(DTO_Comments comments) {
        if (comments == null) {
            return false;
        }
        Comments newComments = new Comments();
        Assets assets = new Assets();
        assets.setAssetId(comments.getAssetId());
        newComments.setAsset(assets);

        Users users = new Users();
        users.setId(comments.getUserId());
        newComments.setUser(users);

        newComments.setType(comments.getType());
        newComments.setComment(comments.getComment());
        newComments.setDateTime(comments.getDateTime());

        commentRepository.save(newComments);
        return true;
    }
}
