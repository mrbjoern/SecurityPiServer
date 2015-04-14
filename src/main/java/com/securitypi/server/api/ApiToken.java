package com.securitypi.server.api;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * API keys are used when communicating with the server API. Keys must be sent in the http header using header param
 * X-Api-Key. Using a secured connection is strongly recommended.
 */
@Entity
@Table(name = "api_token")
public class ApiToken {

	@NotNull
	private String token;

	@NotNull
	private Timestamp created;

	private String friendlyName;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	public ApiToken() {
		created = new Timestamp(System.currentTimeMillis());
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

	public int getId() {
		return id;
	}
}
