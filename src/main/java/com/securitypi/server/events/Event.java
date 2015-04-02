package com.securitypi.server.events;

import com.securitypi.server.TimestampWrapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Stores an event containing a heading, message, timestamp and severity.
 */
@Entity
@Table(name = "events")
public abstract class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
    private String heading;

	@NotNull
    private String message;

	@NotNull
    private String timestamp;

	public Event() {
		timestamp = new TimestampWrapper(new Date()).getFormatedTimestamp();
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

    public String getTimestamp() {
		return timestamp;
    }
}
