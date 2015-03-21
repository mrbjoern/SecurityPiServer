package com.securitypi.server.api;

import java.util.LinkedList;
import java.util.Random;

public class ApiTokenHandler {

	private static LinkedList<ApiToken> tokens;

	private int tokenLength;

	public ApiTokenHandler() {
		tokens = new LinkedList<>();
		tokenLength = 32;
	}

	public String createNewApiToken() {
		String token = generateToken();

		ApiToken apiToken = new ApiToken();
		apiToken.setToken(token);

		addApiToken(apiToken);

		return token;
	}

	public ApiToken getApiToken(String token) {
		for(ApiToken apiToken : tokens) {
			if(apiToken.getToken().equals(token)) {
				return apiToken;
			}
		}
		return new ApiToken();
	}

	private String generateToken() {
		char[] validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-+_!@#$%&[]()?".toCharArray();

		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();

		for(int i = 0; i < tokenLength; i++) {
			char c = validChars[random.nextInt(validChars.length)];
			stringBuilder.append(c);
		}

		return stringBuilder.toString();
	}

	private void addApiToken(ApiToken token) {
		tokens.addFirst(token);
	}
}
