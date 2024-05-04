/**
 * 
 */
package com.sgd.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.service.UserService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}
	
	@PostMapping({"/registerNewAdmin"})
	public User registerNewAdmin(@RequestBody User user) {
		return userService.registerNewAdmin(user);
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('admin')")
	public String forAdmin() {
		return "This is for ADMIN";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('user')")
	public String forUser() {
		return "This is for USER";
	}
}
