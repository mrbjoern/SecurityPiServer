package com.securitypi.server.events;

import com.securitypi.server.TemperatureReading;
import com.securitypi.server.TimestampWrapper;

import java.util.Date;

/**
 * Created by mrbjoern on 3/20/15.
 */
public class TemperatureOutsideOfThresholdEvent extends Event {

	public TemperatureOutsideOfThresholdEvent(TemperatureReading temperatureReading) {
		super.setHeading("Temperature outside of given threshold");
		super.setMessage("The temperature was measured to be " + temperatureReading.getTemperature() + " degrees.");
	}
}
