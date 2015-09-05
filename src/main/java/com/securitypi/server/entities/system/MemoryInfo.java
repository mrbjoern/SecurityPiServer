package com.securitypi.server.entities.system;


public class MemoryInfo {

	private long totalMemory;
	private long freeMemory;

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getFreeMemory() {
		return freeMemory;
	}
}
