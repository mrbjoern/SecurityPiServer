package com.securitypi.server.api.admin;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

	@RequestMapping(value = "generate/token", method = {RequestMethod.GET}, produces = {"application/json"})
	public ResponseEntity<ApiToken> generateToken() {
		ApiToken newToken = ApiTokenHandler.createNewApiToken();

		return new ResponseEntity<>(newToken, HttpStatus.OK);
	}
}
