package com.securitypi.server.api;

import com.securitypi.server.TimestampWrapper;

public class ApiConnection {

	private String sourceIPAddress;
	private String timestamp;
	private String userAgent;

	public ApiConnection() {
		timestamp = new TimestampWrapper().getFormatedTimestamp();
	}

	public void setSourceIPAddress(String IPAddress) {
		sourceIPAddress = IPAddress;
	}

	public String getSourceIPAddress() {
		return sourceIPAddress;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}

}
