package com.securitypi.server.api;

import java.sql.Timestamp;

public class ApiConnection {


	private String sourceIPAddress;
	private Timestamp timestamp;
	private String userAgent;

	public ApiConnection() {
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public void setSourceIPAddress(String IPAddress) {
		sourceIPAddress = IPAddress;
	}

	public String getSourceIPAddress() {
		return sourceIPAddress;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}

}
