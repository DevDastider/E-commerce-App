/**
 * 
 */
package com.sgd.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.service.CartService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
@CrossOrigin("*")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PreAuthorize("hasRole('user')")
	@GetMapping({"/addToCart/{productId}"})
	public Cart addToCart(@PathVariable(name = "productId")Integer productId){
		return cartService.addProductToCart(productId);
	}
	
	@PreAuthorize("hasRole('user')")
	@GetMapping({"/getCartDetails"})
	public List<Cart> getCartDetails(){
		return cartService.getCartDetails();
	}
}
