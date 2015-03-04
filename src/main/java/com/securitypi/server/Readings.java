package com.securitypi.server;

import java.util.LinkedList;

/**
 * Store temperature readings.
 */
public class Readings {

    private static LinkedList<TemperatureReading> readings;

    public Readings() {
        readings = new LinkedList<>();
    }

    public static void addReading(TemperatureReading tr) {
        readings.add(tr);
    }

    public static TemperatureReading getLastReading() {
        return readings.getLast();
    }

    public static LinkedList<TemperatureReading> getAllReadings() {
        return readings;
    }
}
