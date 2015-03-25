package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@Controller
class AdminController {

	@RequestMapping(value = "token/generate", method = {RequestMethod.POST})
	public String generateToken(@RequestParam("name") String name) {
		ApiToken newToken = ApiTokenHandler.createNewApiToken();

		if(!name.isEmpty()) {
			newToken.setFriendlyName(name);
		}

		return "redirect:/admin";
	}

	@RequestMapping(value = "token/revoke/{token}", method = {RequestMethod.POST})
	public String revokeToken(@PathVariable String token) {

		ApiTokenHandler.revokeApiToken(token);

		return "redirect:/admin";
	}

	// Probably deprecated, since handling of tokens should be done through admin pages.
	@RequestMapping(value = "generate/token", method = {RequestMethod.POST}, produces = {"application/json"})
	public ResponseEntity<ApiToken> generateJSONToken(@RequestBody ApiToken customToken) {
		ApiToken newToken = ApiTokenHandler.createNewApiToken();

		if(customToken.getFriendlyName().compareToIgnoreCase("unknown") != 0) {
			newToken.setFriendlyName(customToken.getFriendlyName());
		}

		return new ResponseEntity<>(newToken, HttpStatus.OK);
	}

}
