package com.securitypi.server.events;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles events, writing those to the database and retrieving events to be displayed.
 */
@Repository
@Transactional
public class EventHandler {

	@PersistenceContext
	private EntityManager entityManager;

	// Goes to database later
	private static LinkedList<Event> events;

	public EventHandler() {
		events = new LinkedList<>();

	}

	public static void addEvent(Event event) {
		events.addFirst(event);
	}

	public void addDatabaseEvent(Event event) {
		System.out.println(event.getHeading());
		entityManager.persist(event);
	}

	public static Event getLastEvent() {
		if(events.isEmpty()) {
			return new GenericEvent();
		}
		else {
			return events.getFirst();
		}
	}

	@ModelAttribute("events")
	public static List<Event> getNumberOfEvents(int number) {
		if(events.size() < number) {
			return events;
		}
		else {
			return events.subList(0, number);
		}
	}

	@ModelAttribute("events")
	public static List<Event> getEvents() {
		return events;
	}
}
