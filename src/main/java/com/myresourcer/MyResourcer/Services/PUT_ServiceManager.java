package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PUT_ServiceManager {

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

    public PUT_ServiceManager() {
    }


    public boolean updateRequest(Integer id, DTO_Request requestData) {

        //return if any of these field are null
        if (requestData == null || id == null) {
            return false;
        }
        // Check if the request with the given ID exists
        Optional<Request> existingRequestOpt = requestRepository.findById(id);

        if (existingRequestOpt.isPresent()) {
            Request requestToUpdate = existingRequestOpt.get();

            // Updating fields
            if (requestData.getAssetId() != null) {
                Assets asset = new Assets();
                asset.setAssetId(requestData.getAssetId());
                requestToUpdate.setAssetId(asset);
            }
            if (requestData.getUserId() != null) {
                Users user = new Users();
                user.setId(requestData.getUserId());
                requestToUpdate.setUserId(user);
            }
            if (requestData.getStatusId() != null) {
                Status status = new Status();
                status.setStatusId(requestData.getStatusId());
                requestToUpdate.setStatusId(status);
            }
            if (requestData.getConditionId() != null) {
                Condition condition = new Condition();
                condition.setConditionId(requestData.getConditionId());
                requestToUpdate.setConditionId(condition);
            }
            if (requestData.getDateOut() != null) requestToUpdate.setDateOut(requestData.getDateOut());
            if (requestData.getDateIn() != null) requestToUpdate.setDateIn(requestData.getDateIn());
            if (requestData.getTimeOut() != null) requestToUpdate.setTimeOut(requestData.getTimeOut());
            if (requestData.getTimeIn() != null) requestToUpdate.setTimeIn(requestData.getTimeIn());

            requestRepository.save(requestToUpdate);
            return true;
        }

        return false;
    }

    public boolean updateAsset(Integer id, DTO_Assets assetData){
        //return if any of these field are null
        if(assetData == null || id == null){
            return false;
        }
        // Check if the asset with the given ID exists
        Optional<Assets> existingAssetOpt = assetRepository.findById(id);
        if(existingAssetOpt.isPresent()){
            Assets assetToUpdate = existingAssetOpt.get();
            // Updating fields
            if(assetData.getItem() != null) assetToUpdate.setItem(assetData.getItem());
            if(assetData.getSpecifications() != null) assetToUpdate.setSpecifications(assetData.getSpecifications());
            if(assetData.getSerialNumber() != null) assetToUpdate.setSerialNumber(assetData.getSerialNumber());
            if(assetData.isMobile() != null) assetToUpdate.setMobile(assetData.isMobile());

        }
        return false;
    }

    public boolean updateCategory(Integer id, Categories categoryData){
        //return if any of these field are null
        if(categoryData == null || id == null){
            return false;
            }
        // Check if the category with the given ID exists
        Optional<Categories> existingCategoryOpt = categoryRepository.findById(id);
        if(existingCategoryOpt.isPresent()){
            Categories categoryToUpdate = existingCategoryOpt.get();
            // Updating fields
            if(categoryData.getCategoryName() != null) categoryToUpdate.setCategoryName(categoryData.getCategoryName());
        }
        return false;
    }

    public boolean updateCondition(Integer id, Condition conditionData){
        //return if any of these field are null
        if(conditionData == null || id == null){
            return false;
        }
        // Check if the condition with the given ID exists
        Optional<Condition> existingConditionOpt = conditionRepository.findById(id);
        if(existingConditionOpt.isPresent()){
            Condition conditionToUpdate = existingConditionOpt.get();
            // Updating fields
            if(conditionData.getConditionName() != null) conditionToUpdate.setConditionName(conditionData.getConditionName());
        }
        return false;
    }

    public boolean updateDepartment(Integer id, Departments departmentData){
        //return if any of these field are null
        if(departmentData == null || id == null){
            return false;
        }
        // Check if the department with the given ID exists
        Optional<Departments> existingDepartmentOpt = departmentRepository.findById(id);

        if(existingDepartmentOpt.isPresent()){
            Departments departmentToUpdate = existingDepartmentOpt.get();
            // Updating fields
            if(departmentData.getDepartmentName() != null) departmentToUpdate.setDepartmentName(departmentData.getDepartmentName());
        }
        return false;
    }

    public boolean updateUser(Integer id, Users userData){
        //return if any of these field are null
        if(userData == null || id == null){
            return false;
        }
        // Check if the user with the given ID exists
        Optional<Users> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isPresent()){
            Users userToUpdate = existingUserOpt.get();
            // Updating fields
            if(userData.getUsername() != null) userToUpdate.setUsername(userData.getUsername());
    }
        return false;
    }

    public boolean updateRole(Integer id, Roles roleData){
        //return if any of these field are null
        if(roleData == null || id == null){
            return false;
        }
        // Check if the role with the given ID exists
        Optional<Roles> existingRoleOpt = roleRepository.findById(id);
        if(existingRoleOpt.isPresent()){
            Roles roleToUpdate = existingRoleOpt.get();
            // Updating fields
            if(roleData.getRoleName() != null) roleToUpdate.setRoleName(roleData.getRoleName());
        }
        return  false;
    }

    public boolean updateStatus(Integer id, Status statusData){
        //return if any of these field are null
        if(statusData == null || id == null) {
            return false;
        }
        // Check if the status with the given ID exists
        Optional<Status> existingStatusOpt = statusRepository.findById(id);
        if(existingStatusOpt.isPresent()){
            Status statusToUpdate = existingStatusOpt.get();
            // Updating fields
            if(statusData.getStatusName() != null) statusToUpdate.setStatusName(statusData.getStatusName());
        }
        return false;
    }


}
