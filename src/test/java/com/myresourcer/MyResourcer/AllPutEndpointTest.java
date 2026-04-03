package com.myresourcer.MyResourcer;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.Models.*;
import com.myresourcer.MyResourcer.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class AllPutEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean private Asset_Repository assetRepository;
    @MockitoBean private Category_Repository categoryRepository;
    @MockitoBean private Condition_Repository conditionRepository;
    @MockitoBean private Department_Repository departmentRepository;
    @MockitoBean private Role_Repository roleRepository;
    @MockitoBean private Status_Repository statusRepository;
    @MockitoBean private User_Repository userRepository;
    @MockitoBean private Request_Repository requestRepository;

    @Test
    public void testUpdateRequest() throws Exception {
        Request existingRequest = new Request();
        existingRequest.setRequestId(1);
        when(requestRepository.findById(1)).thenReturn(Optional.of(existingRequest));

        DTO_Request updateData = new DTO_Request();
        updateData.setDateOut("2026-12-12");
        updateData.setTimeOut("12:00:00");

        mockMvc.perform(put("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Request Successfully Updated"));
    }

    @Test
    public void testUpdateAsset() throws Exception {
        Assets existingAsset = new Assets();
        existingAsset.setAssetId(1);
        existingAsset.setItem("Old Asset Name");
        existingAsset.setMobile(true);
        existingAsset.setSerialNumber("SN34323");
        existingAsset.setSpecifications("Specs");
        existingAsset.setCategoryId(new Categories(1, "Test Category"));
        existingAsset.setRemoved(false);

        when(assetRepository.findById(1)).thenReturn(Optional.of(existingAsset));
        when(assetRepository.save(any(Assets.class))).thenAnswer(invocation -> invocation.getArgument(0));

        System.out.println(existingAsset.getAssetId());

        DTO_Assets updateData = new DTO_Assets();
        updateData.setItem("Updated Asset Name");
        updateData.setMobile(false);

        mockMvc.perform(put("/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset Successfully Updated"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Categories existingCategory = new Categories(1, "Old Category");
        existingCategory.setCategoryId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));

        Categories updateData = new Categories(null, "Updated Category Name");

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Successfully Updated"));
    }

    @Test
    public void testUpdateCondition() throws Exception {
        Condition existingCondition = new Condition(1, "Old Condition");
        when(conditionRepository.findById(1)).thenReturn(Optional.of(existingCondition));

        Condition updateData = new Condition(null, "Updated Condition Name");

        mockMvc.perform(put("/conditions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Condition Successfully Updated"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        Departments existingDept = new Departments(1, "Old Dept");
        when(departmentRepository.findById(1)).thenReturn(Optional.of(existingDept));

        Departments updateData = new Departments(null, "Updated Dept Name");

        mockMvc.perform(put("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Department Successfully Updated"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Users existingUser = new Users();
        existingUser.setId(1);
        existingUser.setUsername("olduser");
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        Users updateData = new Users();
        updateData.setUsername("newuser");

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Successfully Updated"));
    }

    @Test
    public void testUpdateRole() throws Exception {
        Roles existingRole = new Roles(1, "Old Role");
        when(roleRepository.findById(1)).thenReturn(Optional.of(existingRole));

        Roles updateData = new Roles(null, "Updated Role Name");

        mockMvc.perform(put("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Role Successfully Updated"));
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Status existingStatus = new Status(1, "Old Status");
        when(statusRepository.findById(1)).thenReturn(Optional.of(existingStatus));

        Status updateData = new Status(null, "Updated Status Name");

        mockMvc.perform(put("/statuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Status Successfully Updated"));
    }

}
