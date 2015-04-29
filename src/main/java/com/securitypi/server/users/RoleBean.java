package com.securitypi.server.users;

import java.util.Map;

/**
 * Bean with valid user roles and access rights. Should probably contain a list if more properties will be added.
 */
public class RoleBean {

	private Map<String, Boolean> roles;

	public RoleBean() {

	}

	public void setRole(String role, boolean value) {
		roles.put(role, value);
	}

	public Map<String, Boolean> getRoles() {
		return roles;
	}
}
