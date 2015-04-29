package com.securitypi.server.controllers;

import com.securitypi.server.users.RoleBean;
import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import com.securitypi.server.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handles requests by authenticated users, making it possible to edit and update a users own profile.
 *
 * Format of URL should be /api/user/{id}/ACTION/FIELD
 *
 * On requests not regarding a specific user, {id} may be omitted. This applies to cases where a new
 * user is created or when a user with admin authority will get a list of all or a subset of users.
 *
 * Where ACTION is not specified, only RequestMethod.GET should be supported. A list of all, or a subset
 * of users is assumed to be returned.
 */
@RequestMapping("api/user")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

	// TODO: Implement better alternative to redirect:referer

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "{id}/change/password", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN') or @userService.canAccessUser(principal, #id)")
	public String changePassword(@PathVariable Long id, @ModelAttribute User fakeUser, HttpServletRequest request) {
		User accessedUser = userHandler.findUserByID(id);

		if(accessedUser == null) {
			return "redirect:/?error";
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(fakeUser.getPassword());
		userHandler.changePassword(accessedUser.getId(), hashedPassword);

		return "redirect:" + request.getHeader("Referer");

	}

	@RequestMapping(value = "{id}/disable", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String disableUser(@PathVariable long id, HttpServletRequest request) {
		userHandler.disableUserByID(id);
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = "{id}/enable", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String enableUser(@PathVariable long id, HttpServletRequest request) {
		userHandler.enableUserByID(id);
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = "create", method =  {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String createUser(@ModelAttribute User newUser, HttpServletRequest request) {

		// TODO: Implement check on valid passwords

		if(newUser.getUsername().length() > 0 && newUser.getPassword().length() > 0) {

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

			return "redirect:" + request.getHeader("Referer") + "?success";
		}

		return "redirect:" + request.getHeader("Referer") + "?error";
	}

	@RequestMapping(value = "{id}/edit/roles")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String grantUserRole(@PathVariable long id, @ModelAttribute RoleBean roles, HttpServletRequest request) {

		User user = userHandler.findUserByID(id);
		Set<UserRole> userRoles = user.getUserRoles();
		Map<String, Boolean> roleBeanRoles = roles.getRoles();

		for(Map.Entry<String, Boolean> entry : roleBeanRoles.entrySet()) {
			String key = entry.getKey();
			boolean state = entry.getValue();
			boolean hasRole = false;

			for(UserRole role : userRoles) {
				if(role.getRole().equals(key)) {
					hasRole = true;
				}
			}
			if(!hasRole && state) {
				UserRole newRole = new UserRole(user, key);
				userHandler.grantUserRole(newRole);
			}

			if(hasRole && !state) {
				// remove role
			}
		}

		return "redirect:" + request.getHeader("Referer");
	}

}
