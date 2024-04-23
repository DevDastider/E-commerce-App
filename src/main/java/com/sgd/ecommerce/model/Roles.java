/**
 * 
 */
package com.sgd.ecommerce.model;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public enum Roles {

	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public static String getRoleLevel(Roles inputRole) {
		for(Roles roleLevel: Roles.values()) {
			if(roleLevel.role.equals(inputRole.role)) {
				return roleLevel.role;
			}
		}
		return null;
	}
}
