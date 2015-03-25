package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/admin")
@Controller
class AdminController {

	@RequestMapping(value = "generate/token", method = {RequestMethod.POST})
	public String generateToken(@RequestParam("name") String name) {
		ApiToken newToken = ApiTokenHandler.createNewApiToken();

		if(!name.isEmpty()) {
			newToken.setFriendlyName(name);
		}

		return "redirect:/admin";
	}

	@RequestMapping(value = "generate/token", method = {RequestMethod.POST}, produces = {"application/json"})
	public ResponseEntity<ApiToken> generateJSONToken(@RequestBody ApiToken customToken) {
		ApiToken newToken = ApiTokenHandler.createNewApiToken();

		if(customToken.getFriendlyName().compareToIgnoreCase("unknown") != 0) {
			newToken.setFriendlyName(customToken.getFriendlyName());
		}

		return new ResponseEntity<>(newToken, HttpStatus.OK);
	}

}
