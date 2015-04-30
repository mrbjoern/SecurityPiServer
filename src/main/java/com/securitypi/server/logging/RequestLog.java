package com.securitypi.server.logging;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "request_log")
public class RequestLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private Timestamp timestamp;

	@NotNull
	private String requestAddress;

	@NotNull
	private String requestUrl;

	private String token;

	private String username;

	public RequestLog() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public long getId() {
		return id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}

	public String getRequestAddress() {
		return requestAddress;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
