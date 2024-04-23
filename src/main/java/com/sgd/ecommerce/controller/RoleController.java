/**
 * 
 */
package com.sgd.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.Role;
import com.sgd.ecommerce.service.RoleService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
}
