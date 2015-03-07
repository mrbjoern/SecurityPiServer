package com.securitypi.server;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Store temperature readings. As for prototyping, a static list should be sufficient,
 * but in production a database should preferable be used.
 */
public class TemperatureReadingsHandler {

    private static ArrayList<TemperatureReading> readings;

    public TemperatureReadingsHandler() {
        readings = new ArrayList<>();
    }

    /**
     * Add a temperature reading to be stored.
     * @param tr TemperatureReading object to be stored
     */
    public static void addReading(TemperatureReading tr) {
        readings.add(tr);
    }

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
    public static double getAverageTemperatureLastHours(int hours) {

        double totalTemp = 0;
        DecimalFormat formatter = new DecimalFormat("#.00");

        for(int i = 0; i < readings.size(); i++) {
            totalTemp += readings.get(i).getTemperature();
        }

        totalTemp = totalTemp / readings.size();
        formatter.format(totalTemp);

        return totalTemp;
    }
}
