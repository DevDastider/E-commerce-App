/**
 * 
 */
package com.sgd.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.RoleDao;
import com.sgd.ecommerce.model.Role;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}

}
