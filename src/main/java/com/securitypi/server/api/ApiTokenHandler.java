package com.securitypi.server.api;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class ApiTokenHandler {

	@PersistenceContext
	private EntityManager entityManager;

	private final int tokenLength = 32;

	public ApiTokenHandler() {
	}

	public ApiToken createNewApiToken(String friendlyName) {
		String token = generateToken();

		ApiToken apiToken = new ApiToken();
		apiToken.setToken(token);

		if(!friendlyName.isEmpty()) {
			apiToken.setFriendlyName(friendlyName);
		}

		addApiToken(apiToken);

		return apiToken;
	}

	public ApiToken getApiToken(String token) {

		ApiToken apiToken = (ApiToken) entityManager.createQuery("from ApiToken where token = :token")
				.setParameter("token", token)
				.getSingleResult();

		if(apiToken == null) {
			return new ApiToken();
		}
		else {
			return apiToken;
		}
	}

	public boolean tokenExist(String token) {
		ApiToken apiToken = getApiToken(token);

		System.out.println(apiToken.toString());

		if(apiToken.getToken() == null) {
			return false;
		}
		else if(apiToken.getToken().equals(token)) {
			return true;
		}

		return false;
	}

	public void revokeApiTokenByToken(String token) {

		ApiToken apiToken = getApiToken(token);

		entityManager.remove(apiToken);
	}

	@ModelAttribute("apitokens")
	public List<ApiToken> getAllApiTokens() {
		return entityManager.createQuery("from ApiToken order by created desc").getResultList();
	}

	private String generateToken() {
		char[] validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$-_+!*'()".toCharArray();

		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();

		for(int i = 0; i < tokenLength; i++) {
			char c = validChars[random.nextInt(validChars.length)];
			stringBuilder.append(c);
		}

		return stringBuilder.toString();
	}

	private void addApiToken(ApiToken token) {
		entityManager.persist(token);
	}
}
