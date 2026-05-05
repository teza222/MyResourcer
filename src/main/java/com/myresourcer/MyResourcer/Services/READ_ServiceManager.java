package com.myresourcer.MyResourcer.Services;

import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class READ_ServiceManager {

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

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public READ_ServiceManager() {
    }



    public List<Request> getAllRequests() {
        String sql = "SELECT * FROM request";

        return jdbcTemplate.query(sql, new RowMapper<Request>() {
            @Override
            public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
                Request request = new Request();
                request.setRequestId(rs.getInt("request_id"));

                Assets asset = new Assets();
                asset.setAssetId(rs.getInt("asset_id"));
                request.setAssetId(asset);

                Users user = new Users();
                user.setId(rs.getInt("id"));  // Changed from "user_id" to "id"
                request.setUserId(user);

                Status status = new Status();
                status.setStatusId(rs.getInt("status_id"));
                request.setStatusId(status);

                Condition condition = new Condition();
                condition.setConditionId(rs.getInt("condition_id"));
                request.setConditionId(condition);

                String dateOut = rs.getString("date_out");
                request.setDateOut(dateOut);

                String dateIn = rs.getString("date_in");
                request.setDateIn(dateIn);

                request.setTimeOut(rs.getString("time_out"));
                request.setTimeIn(rs.getString("time_in"));
                return request;
            }
        });
    }
    public int getRequestTotal(){
        return requestRepository.findAll().size();
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
