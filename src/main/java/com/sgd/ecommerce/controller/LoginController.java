/**
 * 
 */
package com.sgd.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.LoginRequest;
import com.sgd.ecommerce.model.LoginResponse;
import com.sgd.ecommerce.service.JWTService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	private JWTService jwtService;
	
	@PostMapping({"/authenticate"})
	public LoginResponse createJwtToken(@RequestBody LoginRequest loginRequest) throws Exception {
		return jwtService.createJwtToken(loginRequest);
	}
}
