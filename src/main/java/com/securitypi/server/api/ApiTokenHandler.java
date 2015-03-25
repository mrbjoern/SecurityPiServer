package com.securitypi.server.api;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ApiTokenHandler {

	// TODO: Implement database. Add tokens to database and cache. Only read from database when token is not in cache.
	// Cache should probably be something threadsafe like concurrentHashMap.
	private static LinkedList<ApiToken> tokens;

	private static int tokenLength;

	public ApiTokenHandler() {
		tokens = new LinkedList<>();
		tokenLength = 32;
	}

	public static ApiToken createNewApiToken() {
		String token = generateToken();

		ApiToken apiToken = new ApiToken();
		apiToken.setToken(token);

		// Dummy code so connections won't be empty
		ApiConnection connection = new ApiConnection();
		connection.setSourceIPAddress("localhost");
		connection.setUserAgent("SecurityPi Server Admin");

		apiToken.addApiConnection(connection);

		addApiToken(apiToken);

		return apiToken;
	}

	public static ApiToken getApiToken(String token) {
		for(ApiToken apiToken : tokens) {
			if(apiToken.getToken().equals(token)) {
				return apiToken;
			}
		}
		return new ApiToken();
	}

	public static boolean tokenExist(String token) {
		for(ApiToken apiToken : tokens) {
			if(apiToken.getToken().equals(token)) {
				return true;
			}
		}

		return false;
	}

	public static void revokeApiToken(String token) {
		ApiToken apiToken = getApiToken(token);

		tokens.remove(apiToken);
	}

	@ModelAttribute("apitokens")
	public static List<ApiToken> getAllApiTokens() {
		return tokens;
	}

	private static String generateToken() {
		char[] validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$-_+!*'()".toCharArray();

		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();

		for(int i = 0; i < tokenLength; i++) {
			char c = validChars[random.nextInt(validChars.length)];
			stringBuilder.append(c);
		}

		return stringBuilder.toString();
	}

	private static void addApiToken(ApiToken token) {
		tokens.addFirst(token);
	}
}