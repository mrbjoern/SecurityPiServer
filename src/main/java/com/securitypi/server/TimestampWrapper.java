package com.securitypi.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampWrapper {
	private Date timestamp;

	public TimestampWrapper() {
		this.timestamp = new Date();
	}

	// Should only be used if another time than current time is to be set.
	public TimestampWrapper(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getFormatedTimestamp() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simpleDateFormat.format(timestamp);
	}
}
