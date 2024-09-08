/**
 * 
 */
package com.sgd.ecommerce.controller;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.security.JWTAuthenticationHelper;
import com.sgd.ecommerce.service.JWTService;
import com.sgd.ecommerce.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private JWTAuthenticationHelper jwtAuthenticationHelper;
	
	@MockBean
	private JWTService jwtService;

	@InjectMocks
	private UserController userController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testRegisterNewUser() throws Exception {
		User user = new User(); // Populate with test data
		when(userService.registerNewUser(any(User.class))).thenReturn(user);

		mockMvc.perform(post("/registerNewUser").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk());

		verify(userService, times(1)).registerNewUser(any(User.class));
	}

	@Test
	public void testRegisterNewAdmin() throws Exception {
		User user = new User(); // Populate with test data
		when(userService.registerNewAdmin(any(User.class))).thenReturn(user);

		mockMvc.perform(post("/registerNewAdmin").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk());

		verify(userService, times(1)).registerNewAdmin(any(User.class));
	}

	@Test
	@WithMockUser(roles = "admin")
	public void testForAdmin_WithAdminRole() throws Exception {
		mockMvc.perform(get("/forAdmin")).andExpect(status().isOk()).andExpect(content().string("This is for ADMIN"));
	}

	@Test
	@WithMockUser(roles = "user")
	public void testForUser_WithUserRole() throws Exception {
		mockMvc.perform(get("/forUser")).andExpect(status().isOk()).andExpect(content().string("This is for USER"));
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
