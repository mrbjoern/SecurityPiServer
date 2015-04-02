package com.securitypi.server.api;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * API keys are used when communicating with the server API. Keys must be sent in the http header using header param
 * X-Api-Key. Using a secured connection is strongly recommended.
 */
@Entity
@Table(name = "api_token")
public class ApiToken {

	@Id
	private String token;

	@NotNull
	private Timestamp created;

	private String friendlyName;

	//private LinkedList<ApiConnection> connections;

	public ApiToken() {
		//connections = new LinkedList<>();
		created = new Timestamp(new java.util.Date().getTime());
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public String getFriendlyName() {
		if(friendlyName == null) {
			setFriendlyName("Unknown");
		}
		return friendlyName;
	}

	public Timestamp getCreated() {
		return created;
	}
}
