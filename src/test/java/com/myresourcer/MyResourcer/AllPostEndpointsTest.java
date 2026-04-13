package com.myresourcer.MyResourcer;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
import com.myresourcer.MyResourcer.DTOs.DTO_Request;
import com.myresourcer.MyResourcer.DTOs.DTO_Users;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AllPostEndpointsTest {

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
    public void testAddRequest() throws Exception {
        Roles role = new Roles(1, "Test Role");
        Departments dept = new Departments(1, "Test Dept");
        Categories cat = new Categories(1, "Test Category");
        Status status = new Status(1, "Test Status");
        Condition condition = new Condition(1, "Test Condition");

        Users userEntity = new Users(1, "testuser_req", "password", "Test", "User", role, dept, 1);
        DTO_Assets asset = new DTO_Assets(1, "Test Asset", true, "SN_REQ", "Specs", 2, false);

        DTO_Request request = new DTO_Request();
        request.setAssetId(asset.getAssetId());
        request.setUserId(userEntity.getId());
        request.setStatusId(status.getStatusId());
        request.setConditionId(condition.getConditionId());
        request.setDateOut("2026-03-10");
        request.setTimeOut("10:00:00");

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Request Successfully Added"));
    }

    @Test
    public void testAddAsset() throws Exception {
        Categories savedCategory = new Categories(1, "Test Category");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(savedCategory));
        when(assetRepository.save(any(Assets.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DTO_Assets asset = new DTO_Assets();
        asset.setItem("Test Asset");
        asset.setMobile(true);
        asset.setSerialNumber("SN12345");
        asset.setCategoryId(savedCategory.getCategoryId());

        mockMvc.perform(post("/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Asset Successfully Added"));
    }

    @Test
    public void testAddCategory() throws Exception {
        Categories category = new Categories(null, "Test Category");
        when(categoryRepository.save(any(Categories.class))).thenReturn(new Categories(1, "Test Category"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Category Successfully Added"));
    }

    @Test
    public void testAddCondition() throws Exception {
        Condition condition = new Condition(null, "Test Condition");
        when(conditionRepository.save(any(Condition.class))).thenReturn(new Condition(1, "Test Condition"));

        mockMvc.perform(post("/conditions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(condition)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Condition Successfully Added"));
    }

    @Test
    public void testAddDepartment() throws Exception {
        Departments department = new Departments(null, "Test Department");
        when(departmentRepository.save(any(Departments.class))).thenReturn(new Departments(1, "Test Department"));

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Department Successfully Added"));
    }

    @Test
    public void testAddUser() throws Exception {
        DTO_Users userDto = new DTO_Users();
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setFname("Test");
        userDto.setLname("User");
        userDto.setRoleId(1);
        userDto.setDepartmentId(1);
        userDto.setFlag(0);

        // Mock repository save call
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User Successfully Added"));
    }

    @Test
    public void testAddRole() throws Exception {
        Roles role = new Roles(null, "Test Role");
        when(roleRepository.save(any(Roles.class))).thenReturn(new Roles(1, "Test Role"));

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Role Successfully Added"));
    }

    @Test
    public void testAddStatus() throws Exception {
        Status status = new Status(null, "Test Status");
        when(statusRepository.save(any(Status.class))).thenReturn(new Status(1, "Test Status"));

        mockMvc.perform(post("/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(status)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Status Successfully Added"));
    }
}
