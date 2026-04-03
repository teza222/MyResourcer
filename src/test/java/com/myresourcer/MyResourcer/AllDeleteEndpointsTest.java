package com.myresourcer.MyResourcer;

import com.myresourcer.MyResourcer.DTOs.DTO_Assets;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AllDeleteEndpointsTest {

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
    public void testDeleteRequest() throws Exception {
       //checking if the request exists
        when(requestRepository.existsById(1)).thenReturn(true);
        // performing the delete request mock test
        mockMvc.perform(delete("/requests/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testSoftDeleteAsset() throws Exception {
        Assets existingAsset = new Assets();
        existingAsset.setRemoved(true);

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
    public void testDeleteCategory() throws Exception {
        when(categoryRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteCondition() throws Exception {
        when(conditionRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/conditions/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        when(departmentRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/departments/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteRole() throws Exception {
        when(roleRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/roles/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteStatus() throws Exception {
        when(statusRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/statuses/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
