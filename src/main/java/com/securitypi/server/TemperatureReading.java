package com.securitypi.server;

/**
 * Stores reading of temperatures with timestamp to be stored in database or other format.
 */
public class TemperatureReading {

    private double temperature;
    private String timestamp;

    public TemperatureReading() {

    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
