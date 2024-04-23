/**
 * 
 */
package com.sgd.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Entity
public class Role {

	@Id
	private String roleName;
	private String roleDescription;
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDescription() {
		return roleDescription;
	}
	
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	
}
