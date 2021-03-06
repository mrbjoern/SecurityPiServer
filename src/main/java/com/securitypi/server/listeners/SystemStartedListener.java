package com.securitypi.server.listeners;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import com.securitypi.server.securitypi.Connection;
import com.securitypi.server.securitypi.SecurityPi;
import com.securitypi.server.securitypi.SecurityPiHandler;
import com.securitypi.server.users.User;
import com.securitypi.server.users.UserHandler;
import com.securitypi.server.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Mostly debug purpose. Write som system started events to database and set up users properly for testing.
 */
@Component
public class SystemStartedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private EventHandler eventHandler;

	@Autowired
	private UserHandler userHandler;

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@Autowired
	private SecurityPiHandler securityPiHandler;

	private boolean systemStarted = false;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(systemStarted == false) {
			systemStarted = true;

			Event systemStartedEvent = new Event("System started", "The system started successfully.");
			eventHandler.addEvent(systemStartedEvent);

			// Test user creation
			User user = new User();
			user.setUsername("admin");
			String password = "admin";
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(password));
			user.setEnabled(true);

			Set<UserRole> userRoles = new HashSet<>();
			UserRole userRole = new UserRole(user, "ROLE_USER");
			userRoles.add(userRole);
			UserRole adminRole = new UserRole(user, "ROLE_ADMIN");
			userRoles.add(adminRole);

			user.setUserRoles(userRoles);

			userHandler.create(user);
			userHandler.grantUserRole(userRole);
			userHandler.grantUserRole(adminRole);

			// Create a token and add it to securitypi.
			ApiToken token = apiTokenHandler.createNewApiToken("Debug token");
			SecurityPi securityPi = new SecurityPi();
			securityPi.setToken(token);

			securityPiHandler.addNewSecurityPiComponent(securityPi);

			Connection connection = new Connection(securityPi, "127.0.0.1");
			securityPiHandler.addConnection(connection);

		}

	}
}
