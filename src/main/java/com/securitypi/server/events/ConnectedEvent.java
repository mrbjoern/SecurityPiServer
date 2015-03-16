package com.securitypi.server.events;

import com.securitypi.server.TimestampWrapper;

import java.util.Date;

/**
 * Created by mrbjoern on 3/16/15.
 */
public class ConnectedEvent extends Event {

	public ConnectedEvent() {
		String currentTimestamp = new TimestampWrapper(new Date()).getFormatedTimestamp();

		super.setHeading("Connected to SecurityPi");
		super.setMessage("Connection to SecurityPi was established.");
		super.setTimestamp(currentTimestamp);
	}
}
