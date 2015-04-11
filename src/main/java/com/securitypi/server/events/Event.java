package com.securitypi.server.events;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Stores an event containing a heading, message, timestamp and severity.
 */
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
    private String heading;

	@NotNull
    private String message;

	private String severity;

	@NotNull
    private Timestamp timestamp;

	public Event() {
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public Event(String heading, String message) {
		timestamp = new Timestamp(System.currentTimeMillis());
		this.heading = heading;
		this.message = message;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

    public String getHeading() {
		return heading;
    }

	public void setMessage(String message) {
		this.message = message;
	}

    public String getMessage() {
		return message;
    }

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getSeverity() {

		if(isValidSeverity(severity)) {
			return severity;
		}
		else {
			setSeverity("default");
			return severity;
		}
	}

    public Timestamp getTimestamp() {
		return timestamp;
    }

	private boolean isValidSeverity(String severity) {
		String[] validSeverities = {"default", "warning", "danger", "success"};

		if(severity == null) {
			return false;
		}

		for(int i = 0; i < validSeverities.length; i++) {
			if(validSeverities[i].equals(severity)) {
				return true;
			}
		}

		return false;
	}
}
