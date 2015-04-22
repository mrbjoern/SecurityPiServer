package com.securitypi.server.controllers;

import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests by authenticated users, making it possible to edit and update a users own profile.
 */
@RequestMapping("api/user")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "{id}/password/change/{password}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or @userService.canAccessUser(principal, #id)")
	public String changePassword(@PathVariable Long id, @PathVariable String password) {
		User accessedUser = userHandler.findUserByID(id);

		if(accessedUser == null) {
			return "redirect:/?error";
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(password);
		userHandler.changePassword(accessedUser.getId(), hashedPassword);
		return "redirect:/";

	}




}
