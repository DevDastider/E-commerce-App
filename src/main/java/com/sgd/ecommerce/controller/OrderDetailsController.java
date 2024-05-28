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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.OrderDetails;
import com.sgd.ecommerce.model.OrderInput;
import com.sgd.ecommerce.service.OrderDetailsService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
@CrossOrigin("*")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	@PreAuthorize("hasRole('user')")
	@PostMapping({ "/placeOrder/{isCartCheckout}" })
	public void placeOrder(@PathVariable(name = "isCartCheckout") boolean isCartCheckout,
			@RequestBody OrderInput orderInput) {
		orderDetailsService.placeOrder(orderInput, isCartCheckout);
	}
	
	@PreAuthorize("hasRole('user')")
	@GetMapping({"/getOrderDetails"})
	public List<OrderDetails> getOrderDetails() {
		return orderDetailsService.getOrderDetails();
	}
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping({"/getAllOrderDetails"})
	public List<OrderDetails> getAllOrderDetails() {
		return orderDetailsService.getAllOrderDetails();
	}
}
