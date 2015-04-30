package com.securitypi.server.logging;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
}
