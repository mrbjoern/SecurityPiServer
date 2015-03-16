package com.securitypi.server;

import java.util.Date;

/**
 * Maintains the state of the SecurityPi component.
 */
public class SecurityPiHandler {
	private static SecurityPi securityPi;
	private static boolean connected;

	public SecurityPiHandler() {
		connected = false;
	}

	public static void connectSecurityPi(SecurityPi pi) {
		securityPi = pi;
		connected = true;

		addConnectedEvent();
	}

	public static boolean isConnected() {
		return connected;
	}

	private static void addConnectedEvent() {
		String currentTimestamp = new TimestampWrapper(new Date()).getFormatedTimestamp();
		Event connectedEvent = new Event();

		connectedEvent.setHeading("Connected to SecurityPi");
		connectedEvent.setMessage("Connection to SecurityPi was established.");
		connectedEvent.setTimestamp(currentTimestamp);

		EventHandler.addEvent(connectedEvent);
	}

}
