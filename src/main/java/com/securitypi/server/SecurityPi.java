package com.securitypi.server;

/**
 * SecurityPi info object. Contains information about currently connected Pi,
 * reporting intervals etc.
 */
public class SecurityPi {
	private int reportingIntervalInSeconds;
	private String ipAddressToPi;

	public SecurityPi() {

	}

	public int getReportingIntervalInSeconds() {
		return reportingIntervalInSeconds;
	}

	public void setReportingIntervalInSeconds(int intervalInSeconds) {
		reportingIntervalInSeconds = intervalInSeconds;
	}

	public String getIpAddressToPi() {
		return ipAddressToPi;
	}

	public void setIpAddressToPi(String addressToPi) {
		ipAddressToPi = addressToPi;
	}
}
