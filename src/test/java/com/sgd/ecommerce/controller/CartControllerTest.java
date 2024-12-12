/**
 * 
 */
package com.sgd.ecommerce.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.security.JWTAuthenticationHelper;
import com.sgd.ecommerce.service.CartService;
import com.sgd.ecommerce.service.JWTService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@WebMvcTest(CartController.class)
public class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartService cartService;

	@MockBean
	private JWTAuthenticationHelper jwtAuthenticationHelper;

	@MockBean
	private JWTService jwtService;

	@InjectMocks
	private CartController cartController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
	}

	@Test
	@WithMockUser(roles = "user")
	void testAddToCart() throws Exception {
		Cart cart = new Cart(new Product(), new User());
		cart.setId(1);
		when(cartService.addProductToCart(anyInt())).thenReturn(cart);

		mockMvc.perform(MockMvcRequestBuilders.get("/addToCart/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	@WithMockUser(roles = "user")
	void testGetCartDetails() throws Exception {
		List<Cart> carts = Arrays.asList(new Cart(), new Cart());
		when(cartService.getCartDetails()).thenReturn(carts);

		mockMvc.perform(MockMvcRequestBuilders.get("/getCartDetails")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	@WithMockUser(roles = "user")
	void testDeleteCartItem() throws Exception {
		doNothing().when(cartService).deleteCartItem(anyInt());

		mockMvc.perform(MockMvcRequestBuilders.delete("/deleteCartItem/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(cartService, Mockito.times(1)).deleteCartItem(1);
	}
}
