package com.securitypi.server;

/**
 * Stores an event containing a heading, message, timestamp and severity.
 */

public class Event {
    private String heading;
    private String message;
    private String timestamp;

	public Event() {

	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

    public String getHeading() {
        if (heading == null) {
            return "N/A";
        }
        else {
            return heading;
        }
    }

	public void setMessage(String message) {
		this.message = message;
	}

    public String getMessage() {
        if(message == null) {
            return "N/A";
        }
        else {
            return message;
        }
    }

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    public String getTimestamp() {
        if(timestamp == null) {
            return "N/A";
        }
        else {
            return timestamp;
        }
    }
}
