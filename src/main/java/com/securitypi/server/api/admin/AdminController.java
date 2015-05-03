package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.users.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

}
