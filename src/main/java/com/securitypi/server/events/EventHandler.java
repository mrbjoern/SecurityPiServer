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

	public EventHandler() {
	}

	public void addEvent(Event event) {
		entityManager.persist(event);
	}

	/**
	 * Get number of events, sorted ascending by timestamp. Returns number of events, or all events if number is > than
	 * all current events. Returns all events if number == 0.
	 * @param number
	 * @return
	 */
	@ModelAttribute("events")
	public List<Event> getNumberOfEvents(int number) {
		List<Event> lastEvents = new LinkedList<>();

		try {
			String hql = "FROM Event ORDER BY timestamp desc";
			Query query = entityManager.createQuery(hql);

			if(number > 0) {
				query.setFirstResult(0);
				query.setMaxResults(number);
			}

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
