package com.securitypi.server.controllers;

import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import com.securitypi.server.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * To be the new User Controller.
 */
@RequestMapping("api/user")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class UserRestController {

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "/all", method = {RequestMethod.GET}, produces = {"application/json"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getUsers() {
		List<User> users = userHandler.getAllUsers();

		return users;
	}

	@RequestMapping(value = "create", method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> createUser(@Valid @RequestBody User newUser, Errors errors) {

		if(errors.hasErrors()) {
			String username = newUser.getUsername();
			String password = newUser.getPassword();
			if(username == null || password == null) {
				return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
			}
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		newUser.setEnabled(true);

		// Users are only granted the role USER by default.
		Set<UserRole> roles = new HashSet<>();
		UserRole userRole = new UserRole(newUser, "ROLE_USER");
		roles.add(userRole);

		newUser.setUserRoles(roles);

		userHandler.create(newUser);
		userHandler.grantUserRole(userRole);

		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

}
