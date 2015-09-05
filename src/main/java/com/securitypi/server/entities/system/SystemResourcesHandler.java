package com.securitypi.server.entities.system;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class SystemResourcesHandler {

	@PersistenceContext
	private EntityManager entityManager;

	public String getDatabaseName() {
		String databaseName = entityManager.createNativeQuery("select current_database();").getSingleResult().toString();
		return databaseName;
	}

	public String getDatabaseUser() {
		String databaseUser = entityManager.createNativeQuery("select current_user;").getSingleResult().toString();
		return databaseUser;
	}

	public String getDatabaseSize() {
		String databaseName = entityManager.createNativeQuery("select current_database();").getSingleResult().toString();
		String databaseSize = entityManager.createNativeQuery("select pg_size_pretty(pg_database_size(:database));")
				.setParameter("database", databaseName).getSingleResult().toString();
		return databaseSize;
	}

	public long getTotalMemoryInMB() {
		long total = Runtime.getRuntime().totalMemory();
		return total/1024/1024;
	}

	public long getFreeMemoryInMB() {
		long free = Runtime.getRuntime().freeMemory();
		return free/1024/1024;
	}
}
