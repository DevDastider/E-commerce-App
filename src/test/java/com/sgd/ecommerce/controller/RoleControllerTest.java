package com.sgd.ecommerce.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgd.ecommerce.model.Role;
import com.sgd.ecommerce.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();

        role = new Role();
        role.setRoleDescription("Role for Admin");
        role.setRoleName("ADMIN");
    }

    @Test
    void testCreateNewRole() throws Exception {
        when(roleService.createNewRole(role)).thenReturn(role);

        mockMvc.perform(post("/createNewRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(role)))
                .andExpect(status().isOk());
    }

    @Test
    void testCORSConfiguration() throws Exception {
        when(roleService.createNewRole(role)).thenReturn(role);

        mockMvc.perform(post("/createNewRole")
                        .header("Origin", "http://test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(role)))
                .andExpect(status().isOk());
    }

    // Utility method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}