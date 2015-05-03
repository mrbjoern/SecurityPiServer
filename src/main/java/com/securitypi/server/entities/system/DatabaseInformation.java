package com.securitypi.server.entities.system;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DatabaseInformation {

	@PersistenceContext
	private EntityManager entityManager;

	private String databaseName;
	private String databaseUser;
	private String databaseSize;

	public DatabaseInformation() {

	}

	public String getDatabaseName() {
		databaseName = entityManager.createNativeQuery("select current_database();").getSingleResult().toString();
		return databaseName;
	}

	public String getDatabaseUser() {
		databaseUser = entityManager.createNativeQuery("select current_user;").getSingleResult().toString();
		return databaseUser;
	}

	public String getDatabaseSize() {
		databaseSize = entityManager.createNativeQuery("select pg_size_pretty(pg_database_size(:database));")
				.setParameter("database", databaseName).getSingleResult().toString();
		return databaseSize;
	}
}
