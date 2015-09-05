package com.securitypi.server.entities.system;

public class DatabaseInfo {

	private String databaseName;
	private String databaseUser;
	private String databaseSize;

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseSize(String databaseSize) {
		this.databaseSize = databaseSize;
	}

	public String getDatabaseSize() {
		return databaseSize;
	}
}
