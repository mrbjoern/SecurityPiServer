package com.securitypi.server.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Remove entries older than n-days at scheduled events.
 */
@Component
public class ScheduledLogRemoval {

	@Autowired
	private LogHandler logHandler;

	// TODO: Number of days should probably be possible to set in application.properties
	@Scheduled(cron = "0 0 * * * *")
	public void clearEntriesOlderThan30Days() {
		logHandler.removeRequestEntriesOlderThan(30);
	}
}
