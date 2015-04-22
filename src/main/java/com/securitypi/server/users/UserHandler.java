package com.securitypi.server.users;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class UserHandler {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(User user)  {
		entityManager.persist(user);
	}

	public void grantUserRole(UserRole role) {
		entityManager.persist(role);
	}

	public User findByUsername(String username) {

		List<User> users = entityManager.createQuery("from User where username = :user")
				.setParameter("user", username)
				.getResultList();

		if(users.size() > 0) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	public User findUserByID(long id) throws UsernameNotFoundException {
		User user = entityManager.find(User.class, id);

		if(user == null) {
			throw new UsernameNotFoundException("The user does not exist.");
		}

		return user;
	}

	@ModelAttribute("users")
	public List<User> getAllUsers() {
		List<User> users;

		users = entityManager.createQuery("from User").getResultList();

		return users;
	}

	public void disableUserByID(long id) {
		User user = entityManager.find(User.class, id);

		user.setEnabled(false);
	}

	public void enableUserByID(long id) {
		User user = entityManager.find(User.class, id);

		user.setEnabled(true);
	}

	public void changePassword(long id, String hashedPassword) {
		User currentUser = entityManager.find(User.class, id);

		currentUser.setPassword(hashedPassword);
	}
}
