package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UPDATE_ServiceManager {

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
    JdbcTemplate jdbcTemplate;

    public UPDATE_ServiceManager() {
    }


    public boolean updateRequest(Integer id, DTO_Request requestData) {

        if (requestData == null || id == null) {
            return false;
        }

        // First check if the request exists
        String checkSql = "SELECT COUNT(*) FROM request WHERE request_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{id}, Integer.class);

        if (count == null || count == 0) {
            return false;
        }
        StringBuilder sql = new StringBuilder("UPDATE request SET ");
        List<Object> params = new ArrayList<>();

        // Only update fields that are not null
        if (requestData.getAssetId() != null) {
            sql.append("asset_id = ?, ");
            params.add(requestData.getAssetId());
        }
        if (requestData.getUserId() != null) {
            sql.append("user_id = ?, ");
            params.add(requestData.getUserId());
        }
        if (requestData.getStatusId() != null) {
            sql.append("status_id = ?, ");
            params.add(requestData.getStatusId());
        }
        if (requestData.getConditionId() != null) {
            sql.append("condition_id = ?, ");
            params.add(requestData.getConditionId());
        }
        if (requestData.getDateOut() != null) {
            sql.append("date_out = ?, ");
            params.add(requestData.getDateOut());
        }
        if (requestData.getDateIn() != null) {
            sql.append("date_in = ?, ");
            params.add(requestData.getDateIn());
        }
        if (requestData.getTimeOut() != null) {
            sql.append("time_out = ?, ");
            params.add(requestData.getTimeOut());
        }
        if (requestData.getTimeIn() != null) {
            sql.append("time_in = ?, ");
            params.add(requestData.getTimeIn());
        }

        if (params.isEmpty()) {
            return false;
        }
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE request_id = ?");
        params.add(id);
        int result = jdbcTemplate.update(sql.toString(), params.toArray());
        if(result > 0){
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
            if(assetData.getMobile() != null) assetToUpdate.setMobile(assetData.getMobile());
            assetRepository.save(assetToUpdate);
            return true;

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
            categoryRepository.save(categoryToUpdate);
            return true;
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
            conditionRepository.save(conditionToUpdate);
            return true;
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
            departmentRepository.save(departmentToUpdate);
            return true;
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
            if(userData.getUsername() != null) {
                userToUpdate.setUsername(userData.getUsername());
                userRepository.save(userToUpdate);
                return true;
            }

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
            roleRepository.save(roleToUpdate);
            return true;
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
            statusRepository.save(statusToUpdate);
            return true;
        }
        return false;
    }


}
