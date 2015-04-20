package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import com.securitypi.server.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/admin")
@Controller
class AdminController {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@Autowired
	private UserHandler userHandler;

	@RequestMapping(value = "token/generate", method = {RequestMethod.POST})
	public String generateToken(@RequestParam("name") String name) {
		ApiToken newToken = apiTokenHandler.createNewApiToken(name);

		return "redirect:/admin";
	}

	@RequestMapping(value = "token/revoke/{token}", method = {RequestMethod.POST})
	public String revokeToken(@PathVariable String token) {

		apiTokenHandler.revokeApiTokenByToken(token);

		return "redirect:/admin";
	}

	@RequestMapping(value = "user/create", method =  {RequestMethod.POST})
	public String createUser(@ModelAttribute User user) {
		if(user.getUsername().length() > 0 && user.getPassword().length() > 0) {

			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			user.setEnabled(true);

			Set<UserRole> roles = new HashSet<>();
			// Users are only granted the role USER by default.
			UserRole userRole = new UserRole(user, "ROLE_USER");
			roles.add(userRole);

			user.setUserRoles(roles);

			userHandler.create(user);
			userHandler.grantUserRole(userRole);

			return "redirect:/admin?success";
		}

		return "redirect:/admin?error";
	}

	@RequestMapping(value = "user/disable/{userID}", method = {RequestMethod.POST})
	public String disableUser(@PathVariable long userID) {
		userHandler.disableUserByID(userID);
		return "redirect:/admin";
	}

	@RequestMapping(value = "user/enable/{userID}", method = {RequestMethod.POST})
	public String enableUser(@PathVariable long userID) {
		userHandler.enableUserByID(userID);
		return "redirect:/admin";
	}

}
