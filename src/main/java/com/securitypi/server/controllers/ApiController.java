package com.securitypi.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api")
@Controller
public class ApiController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String apiIndex() {
		return "api/api";
	}
}
