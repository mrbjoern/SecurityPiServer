package com.securitypi.server;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Store temperature readings. As for prototyping, a static linked list should be sufficient,
 * but in production a database should preferable be used.
 */
public class Readings {

    private static ArrayList<TemperatureReading> readings;

    public Readings() {
        readings = new ArrayList<>();
    }

    /**
     * Add a temperature reading to be stored.
     * @param tr TemperatureReading object to be stored
     */
    public static void addReading(TemperatureReading tr) {
        readings.add(tr);
    }

    /**
     * Get the last reading.
     * @return last reading
     */
    public static TemperatureReading getLastReading() {
        if(readings.size() == 0) {
            // Return an empty object to avoid many exceptions
            return new TemperatureReading();
        }
        else {
            return readings.get(readings.size()-1);
        }
    }

    /**
     * Get the last n number of temperature readings. If the number of readings
     * are less than the number provided, all readings will be returned.
     * @param number The number of readings to be returned
     * @return A linked list of readings to be returned sorted by latest reading first.
     */
    public static LinkedList<TemperatureReading> getReadings(int number) {
        LinkedList<TemperatureReading> list = new LinkedList<>();

        if(number < readings.size()) {
            number = readings.size()-1;
        }

        for(int i = number; i >= 0; i--) {
            list.add(readings.get(i));
        }

        return list;
    }

    /**
     * Get the average temperature in a given amount of hours.
     * @param hours Number of hours. 0 indicate all data.
     * @return The average temperature.
     */
    public static double getAverageTemperature(int hours) {

        if(readings.size() == 0) {
            return 0.0;
        }

        double totalTemp = 0;

        for(int i = 0; i < readings.size(); i++) {
            totalTemp += readings.get(i).getTemperature();
        }

        return totalTemp / readings.size();
    }
}
