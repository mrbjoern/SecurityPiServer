package com.securitypi.server;

import com.securitypi.server.events.EventHandler;
import com.securitypi.server.events.TemperatureBackToNormalEvent;
import com.securitypi.server.events.TemperatureOutsideOfThresholdEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Store temperature readings. As for prototyping, a static list should be sufficient,
 * but in production a database should preferable be used.
 */
public class TemperatureReadingsHandler {

    private static ArrayList<TemperatureReading> readings;

	private static final double MINTEMP = 15.0;
	private static final double MAXTEMP = 30.0;

	private static boolean temperatureWarningIssued;
	private static boolean temperatureBackToNormalIssued;

    public TemperatureReadingsHandler() {
        readings = new ArrayList<>();
		temperatureWarningIssued = false;
		temperatureBackToNormalIssued = true;
    }

    /**
     * Add a temperature reading to be stored.
     * @param temperatureReading TemperatureReading object to be stored
     */
    public static void addReading(TemperatureReading temperatureReading) {
		checkTemperature(temperatureReading);
        readings.add(temperatureReading);
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

	private static void checkTemperature(TemperatureReading temperatureReading) {
		if(temperatureReading.getTemperature() < MINTEMP || temperatureReading.getTemperature() > MAXTEMP) {
			setWarningEvent(temperatureReading);
			temperatureBackToNormalIssued = false;
		}
		else {
			// If the temperature is within the given threshold, knowledge of previous warning events should be cleared.
			setBackToNormalEvent();
			temperatureWarningIssued = false;
		}
	}

	private static void setWarningEvent(TemperatureReading temperatureReading) {
		if(!temperatureWarningIssued) {
			EventHandler.addEvent(new TemperatureOutsideOfThresholdEvent(temperatureReading));
			temperatureWarningIssued = true;
		}
	}

	private static void setBackToNormalEvent() {
		if(!temperatureBackToNormalIssued) {
			EventHandler.addEvent(new TemperatureBackToNormalEvent());
			temperatureBackToNormalIssued = true;
		}

	}
}
