/**
 * 
 */
package com.sgd.ecommerce.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgd.ecommerce.exception.ExceptionHandlerMapping;
import com.sgd.ecommerce.exception.UserNotFoundException;
import com.sgd.ecommerce.model.LoginRequest;
import com.sgd.ecommerce.model.LoginResponse;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.service.JWTService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class LoginControllerTest {

	private MockMvc mockMvc;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController)
								.setControllerAdvice(new ExceptionHandlerMapping())
								.build();
    }

    @Test
    public void testCreateJwtToken_Success() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("testuser");
        loginRequest.setUserPassword("password");

        LoginResponse loginResponse = new LoginResponse(new User(), "dummy-jwt-token");

        Mockito.when(jwtService.createJwtToken(Mockito.any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").value("dummy-jwt-token"));
    }

    @Test
    public void testCreateJwtToken_UserNotFoundException() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("invaliduser");
        loginRequest.setUserPassword("password");

		Mockito.when(jwtService.createJwtToken(Mockito.any(LoginRequest.class))).thenThrow(
				new UserNotFoundException("Bad credentials from user", new BadCredentialsException("User not found")));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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
