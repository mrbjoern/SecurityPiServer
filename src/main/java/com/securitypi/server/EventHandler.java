package com.securitypi.server;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles events, writing those to the database and retrieving events to be displayed.
 */
public class EventHandler {
	private static LinkedList<Event> events;

	public EventHandler() {
		events = new LinkedList<>();

	}

	public static void addEvent(Event event) {
		events.addFirst(event);
	}

	public static Event getLastEvent() {
		if(events.isEmpty()) {
			return new Event();
		}
		else {
			return events.getFirst();
		}
	}

	public static List<Event> getNumberOfEvents(int number) {
		number--;
		return events.subList(0, number);
	}
}
