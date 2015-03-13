package com.securitypi.server;

/**
 * Stores an event containing a heading, message, timestamp and severity.
 */
public class Event {
    private String heading;
    private String message;
    private String timestamp;
    private String severity;

	private String[] validSeverities = {"info", "success", "danger", "warning"};

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

	public void setSeverity(String severity) {
		if(validSeverity(severity)) {
			this.severity = severity;
		}
		else {
			this.severity = "";
		}
	}

    public String getSeverity() {
        if(severity == null) {
            // Default value.
            return "info";
        }
        else {
            return severity;
        }
    }

	private boolean validSeverity(String severity) {
		for(int i = 0; i < validSeverities.length; i++) {
			if(severity.compareTo(validSeverities[i]) == 0) {
				return true;
			}
		}
		return false;
	}
}
