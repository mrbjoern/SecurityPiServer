package com.securitypi.server.events;


/**
 * Created by mrbjoern on 3/16/15.
 */
public class ConnectedEvent extends Event {

	public ConnectedEvent() {
		super.setHeading("Connected to SecurityPi");
		super.setMessage("Connection to SecurityPi was established.");
	}
}
