package com.securitypi.server;

import com.securitypi.server.events.ConnectedEvent;
import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;

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

		EventHandler.addEvent(new ConnectedEvent());
	}

	public static boolean isConnected() {
		return connected;
	}
}
