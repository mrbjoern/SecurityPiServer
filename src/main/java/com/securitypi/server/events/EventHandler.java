package com.securitypi.server.events;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	public void addEvent(Event event) {
		System.out.println("In the add event method.");
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
	public List<Event> getNumberOfEvents(int number) {
		List<Event> lastEvents = new LinkedList<>();

		try {
			String hql = "FROM Event ORDER BY timestamp desc";
			Query query = entityManager.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(number);

			lastEvents = query.getResultList();

			return lastEvents;
		}
		catch (Exception e) {
			return lastEvents;
		}
	}

	@ModelAttribute("events")
	public List<Event> getEvents() {
		List<Event> allEvents = new LinkedList<>();

		try {
			String hql = "FROM Event ORDER BY timestamp desc";
			Query query = entityManager.createQuery(hql);

			allEvents = query.getResultList();

			return allEvents;
		}
		catch (Exception e) {
			return allEvents;
		}
	}
}
