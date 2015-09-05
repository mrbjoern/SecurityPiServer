package com.securitypi.server.controllers;

import com.securitypi.server.entities.system.DatabaseInfo;
import com.securitypi.server.entities.system.MemoryInfo;
import com.securitypi.server.entities.system.SystemResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles all information related to System resources, including DB, allocated memory etc.
 */
@RequestMapping("/api/resources")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class ResourceController {

	@Autowired
	private SystemResourcesHandler systemResourcesHandler;

	@RequestMapping(value = "/database", method = {RequestMethod.GET}, produces = "application/json")
	public DatabaseInfo getDatabaseInfo() {
		DatabaseInfo databaseInfo = new DatabaseInfo();
		databaseInfo.setDatabaseName(systemResourcesHandler.getDatabaseName());
		databaseInfo.setDatabaseUser(systemResourcesHandler.getDatabaseUser());
		databaseInfo.setDatabaseSize(systemResourcesHandler.getDatabaseSize());

		return databaseInfo;
	}

	@RequestMapping(value = "/memory", method = {RequestMethod.GET}, produces = "application/json")
	public MemoryInfo getMemoryInfo() {
		MemoryInfo memoryInfo = new MemoryInfo();
		memoryInfo.setTotalMemory(systemResourcesHandler.getTotalMemoryInMB());
		memoryInfo.setFreeMemory(systemResourcesHandler.getFreeMemoryInMB());

		return memoryInfo;
	}
}
