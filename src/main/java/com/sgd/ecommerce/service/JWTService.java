/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.model.LoginRequest;
import com.sgd.ecommerce.model.LoginResponse;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.security.JWTAuthenticationHelper;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class JWTService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JWTAuthenticationHelper jwtAuthenticationHelper;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username).get();
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("Username is not valid");
		}
	}

	public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
		String userName = loginRequest.getUserName();
		String userPassword = loginRequest.getUserPassword();
		authenticate(userName, userPassword);
		final UserDetails userDetails = loadUserByUsername(userName);
		String jwtToken = jwtAuthenticationHelper.generateToken(userDetails);
		User user = userDao.findById(userName).get();
		user.setUserPassword(null);
		return new LoginResponse(user, jwtToken);
	}

	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException ex) {
			throw new Exception("User is disabled");
		} catch (BadCredentialsException ex) {
			throw new Exception("Bad credentials from user");
		}
	}

	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}
}
