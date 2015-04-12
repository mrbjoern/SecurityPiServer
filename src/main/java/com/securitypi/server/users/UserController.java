package com.securitypi.server.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class UserController {

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createUser() {
		// Test user creation
		User user = new User();
		user.setUsername("admin");
		String password = "admin";
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		user.setEnabled(true);

		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(user, "ROLE_USER");
		userRoles.add(userRole);
		UserRole adminRole = new UserRole(user, "ROLE_ADMIN");
		userRoles.add(adminRole);

		user.setUserRoles(userRoles);

		userHandler.create(user);
		userHandler.grantUserRole(userRole);
		userHandler.grantUserRole(adminRole);

		return "User created";
	}




}
