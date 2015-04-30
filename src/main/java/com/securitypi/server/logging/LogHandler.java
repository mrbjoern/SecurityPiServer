package com.securitypi.server.logging;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@Transactional
public class LogHandler {

	@PersistenceContext
	private EntityManager entityManager;

	public LogHandler() {
	}

	public void addRequestLog(RequestLog newLogEntry) {
		entityManager.persist(newLogEntry);
	}

	public List<RequestLog> getLatestLogEntries(int number) {
		List<RequestLog> latestEntries = entityManager.createQuery("from RequestLog order by timestamp desc")
				.setMaxResults(number)
				.getResultList();
		if(latestEntries.isEmpty()) {
			RequestLog emptyLog = new RequestLog();
			latestEntries.add(emptyLog);
		}
		return latestEntries;
	}

	public void removeRequestEntriesOlderThan(long days) {
		long daysInMillis = TimeUnit.DAYS.toMillis(days);
		Timestamp oldestAllowedEntry = new Timestamp(System.currentTimeMillis()-daysInMillis);


		List<RequestLog> entriesToBeRemoved = entityManager.createQuery("from RequestLog where timestamp < :oldestTimestamp")
				.setParameter("oldestTimestamp", oldestAllowedEntry)
				.getResultList();

		for(RequestLog entryToBeRemoved : entriesToBeRemoved) {
			entityManager.remove(entryToBeRemoved);
		}
	}

	// TODO: Add function to retrieve n-last logs and delete n-days oldest logs.
}
