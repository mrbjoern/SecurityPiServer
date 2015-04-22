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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests by authenticated users, making it possible to edit and update a users own profile.
 */
@RequestMapping("api/user")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "{id}/password/change", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN') or @userService.canAccessUser(principal, #id)")
	public String changePassword(@PathVariable Long id, @ModelAttribute User fakeUser, HttpServletRequest request) {
		User accessedUser = userHandler.findUserByID(id);

		if(accessedUser == null) {
			return "redirect:/?error";
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(fakeUser.getPassword());
		userHandler.changePassword(accessedUser.getId(), hashedPassword);

		// Redirect to referer is not the best option tho.
		// TODO: Implement better function later.
		return "redirect:" + request.getHeader("Referer");

	}




}
