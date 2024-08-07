/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.model.Role;
import com.sgd.ecommerce.model.RoleDescription;
import com.sgd.ecommerce.model.Roles;
import com.sgd.ecommerce.model.User;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public User registerNewUser(final User user) {
		Role userRole = new Role();
		userRole.setRoleName(Roles.getRoleLevel(Roles.USER));
		userRole.setRoleDescription(RoleDescription.getRoleDescription(RoleDescription.USER));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		user.setRole(userRoles);
		user.setUserPassword(getEncryptedPassword(user.getUserPassword()));
		return userDao.save(user);
	}

	public User registerNewAdmin(final User user) {
		Role adminRole = new Role();
		adminRole.setRoleName(Roles.getRoleLevel(Roles.ADMIN));
		adminRole.setRoleDescription(RoleDescription.getRoleDescription(RoleDescription.ADMIN));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		user.setRole(adminRoles);
		user.setUserPassword(getEncryptedPassword(user.getUserPassword()));
		return userDao.save(user);
	}
	
	private String getEncryptedPassword(String password) {
		LOGGER.debug("Encrypting password");
		return passwordEncoder.encode(password);
	}
}
