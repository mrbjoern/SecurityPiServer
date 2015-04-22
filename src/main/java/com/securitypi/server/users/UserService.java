package com.securitypi.server.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

	@Autowired
	private UserHandler userHandler;

	public boolean canAccessUser(org.springframework.security.core.userdetails.User user, long id) {
		User accessedUser = userHandler.findUserByID(id);

		return accessedUser != null && accessedUser.getUsername().equals(user.getUsername());
	}
}
