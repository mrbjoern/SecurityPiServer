package com.securitypi.server.api;

import java.util.LinkedList;
import java.util.List;

/**
 * API keys are used when communicating with the server API. Keys must be sent in the http header, and using a secured
 * connection is strongly recommended.
 */
public class ApiToken {

	private String token;

	private LinkedList<ApiConnection> connections;

	public ApiToken() {
		connections = new LinkedList<>();
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void addApiConnection(ApiConnection currentConnection) {
		connections.addFirst(currentConnection);
	}

	public List<ApiConnection> getAllConnections() {
		return connections;
	}

	public ApiConnection getLastConnection() {
		return connections.getFirst();
	}
}
