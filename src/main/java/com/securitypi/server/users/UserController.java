package com.securitypi.server.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createUser() {
		// Test user creation
		User user = new User();
		user.setUsername("Poop");
		user.setPassword("pooop");
		userDao.create(user);

		return "User created";
	}




}
