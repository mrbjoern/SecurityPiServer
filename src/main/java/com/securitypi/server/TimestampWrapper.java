package com.securitypi.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampWrapper {
	private Date timestamp;

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
