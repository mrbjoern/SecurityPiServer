package com.securitypi.server.events;

public class SecurityPiEvent extends Event {

	public void setHeading(String heading) {
		super.setHeading(heading);
	}

	public String getHeading() {
		return super.getHeading();
	}

	public void setMessage(String message) {
		super.setMessage(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
}
