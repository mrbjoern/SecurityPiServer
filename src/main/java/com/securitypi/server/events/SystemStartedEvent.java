package com.securitypi.server.events;

import com.securitypi.server.TimestampWrapper;

import java.util.Date;

/**
 * Created by mrbjoern on 3/16/15.
 */
public class SystemStartedEvent extends Event {

	public SystemStartedEvent() {
		super.setHeading("System started");
		super.setMessage("The system started successfully.");
	}
}
