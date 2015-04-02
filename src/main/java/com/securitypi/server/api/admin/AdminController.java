package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.events.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@Controller
class AdminController {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@RequestMapping(value = "token/generate", method = {RequestMethod.POST})
	public String generateToken(@RequestParam("name") String name) {
		ApiToken newToken = apiTokenHandler.createNewApiToken(name);

		return "redirect:/admin";
	}

	@RequestMapping(value = "token/revoke/{token}", method = {RequestMethod.POST})
	public String revokeToken(@PathVariable String token) {

		apiTokenHandler.revokeApiToken(token);

		return "redirect:/admin";
	}

}
