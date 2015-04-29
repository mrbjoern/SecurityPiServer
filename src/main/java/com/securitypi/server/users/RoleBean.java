package com.securitypi.server.users;

import java.util.LinkedList;
import java.util.List;

/**
 * Bean with valid user roles and access rights. Should probably contain a list if more properties will be added.
 */
public class RoleBean {

	private List<String> userRoles;

	public RoleBean() {
		userRoles = new LinkedList<>();
	}

	public void setRole(String role) {
		userRoles.add(role);
	}

	public void setUserRoles(List<String> roles) {
		this.userRoles = roles;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}
}
