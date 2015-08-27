package com.securitypi.server.controllers;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/token/*")
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TokenController {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@RequestMapping(value = "/", method = {RequestMethod.GET}, produces = {"application/json"})
	public List<ApiToken> getAllTokens() {
		return apiTokenHandler.getAllApiTokens();
	}

	@RequestMapping(value = "/generate", method = {RequestMethod.POST}, produces = {"application/json"})
	public ApiToken generateToken(@RequestParam(value = "name", defaultValue = "") String name) {
		return  apiTokenHandler.createNewApiToken(name);
	}


}
