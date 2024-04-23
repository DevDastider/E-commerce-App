/**
 * 
 */
package com.sgd.ecommerce.model;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public enum RoleDescription {
	
	ADMIN("Admin role for E-Commerce Application"),
	USER("Default role for newly created users of E-Commerce Application");
	
	private String roleDescription;
	
	private RoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	public static String getRoleDescription(RoleDescription inputRole) {
		for(RoleDescription roleLevel: RoleDescription.values()) {
			if(roleLevel.roleDescription.equals(inputRole.roleDescription)) {
				return roleLevel.roleDescription;
			}
		}
		return null;
	}

}
