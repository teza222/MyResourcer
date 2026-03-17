package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.Models.Assets;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DELETE_ServiceManager {
    @Autowired
    private Request_Repository requestRepository;
    @Autowired
    private Asset_Repository assetRepository;
    @Autowired
    private Category_Repository categoryRepository;
    @Autowired
    private Condition_Repository conditionRepository;
    @Autowired
    private Department_Repository departmentRepository;
    @Autowired
    private User_Repository userRepository;
    @Autowired
    private Role_Repository roleRepository;
    @Autowired
    private Status_Repository statusRepository;

    public DELETE_ServiceManager() {
    }

    public boolean deleteRequest(Integer id) {
        if (requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean softDeleteAsset(Integer id) {
        if (assetRepository.existsById(id)) {
            Optional<Assets> existingAssetOpt = assetRepository.findById(id);
            if(existingAssetOpt.isPresent()){
                Assets assetToUpdate = existingAssetOpt.get();
                // Updating field to soft-delete assets
                assetToUpdate.setRemoved(true);
                assetRepository.save(assetToUpdate);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteCondition(Integer id) {
        if (conditionRepository.existsById(id)) {
            conditionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(Integer id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteRole(Integer id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteStatus(Integer id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
