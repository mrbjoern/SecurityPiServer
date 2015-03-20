package com.securitypi.server.events;

/**
 * Created by mrbjoern on 3/20/15.
 */
public class TemperatureBackToNormalEvent extends Event {

	public TemperatureBackToNormalEvent() {
		super.setHeading("Temperature within given threshold");
		super.setMessage("The temperature have stabilized and is within the given threshold.");
	}
}
