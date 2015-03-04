package com.securitypi.server;

/**
 * Stores an event containing a heading, message, timestamp and severity.
 */
public class Event {
    private String heading;
    private String message;
    private String timestamp;
    private String severity;

    public Event(String heading, String message, String timestamp, String severity) {
        this.heading = heading;
        this.message = message;
        this.timestamp = timestamp;
        this.severity = severity;
    }

    public String getHeading() {
        if (heading == null) {
            return "N/A";
        }
        else {
            return heading;
        }
    }

    public String getMessage() {
        if(message == null) {
            return "N/A";
        }
        else {
            return message;
        }
    }

    public String getTimestamp() {
        if(timestamp == null) {
            return "N/A";
        }
        else {
            return timestamp;
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
}
