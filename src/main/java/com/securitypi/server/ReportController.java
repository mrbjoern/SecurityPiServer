package com.securitypi.server;

import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * Reporting controller for SecurityPi Server. Handles reporting of events from SecirutyPi app or other devices.
 */
@RequestMapping("/api/report/**")
@RestController
public class ReportController {

	@Autowired
	private TemperatureReadingsHandler temperatureReadingsHandler;

	@Autowired
	private EventHandler eventHandler;

	// Currently, an empty JSON body is supported, resulting in TemperatureReading objects with default temperature 0.0
	// TODO: Implement handling for empty temperature reading objects.
    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@RequestBody TemperatureReading currentReading) {

		// As default, timestamp should not be set by sender, but handled by server.
		if(currentReading.getTimestamp() == null) {
			currentReading.setTimestamp(new Timestamp(new java.util.Date().getTime()));
		}

        temperatureReadingsHandler.addReading(currentReading);

        return new ResponseEntity<>(currentReading, HttpStatus.OK);
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.GET}, produces = {"application/json"})
    public TemperatureReading getReading() {
         return temperatureReadingsHandler.getLastReading();
    }

	@RequestMapping(value = "/event", method = RequestMethod.POST, produces = {"application/json"})
	public ResponseEntity<Event> reportEvent(@RequestBody Event event) {

		if(event.getHeading() == null || event.getMessage() == null) {
			return new ResponseEntity<>(new Event(), HttpStatus.BAD_REQUEST);
		}

		eventHandler.addEvent(event);

		return new ResponseEntity<>(event, HttpStatus.OK);
	}
}
