package com.securitypi.server;

import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import com.securitypi.server.temperatures.TemperatureReading;
import com.securitypi.server.temperatures.TemperatureReadingsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@Valid @RequestBody TemperatureReading currentReading, Errors errors) {

		if(errors.hasErrors()) {
			if(currentReading.getTemperature() == 0.0) {
				return new ResponseEntity<>(currentReading, HttpStatus.BAD_REQUEST);
			}
		}

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
			return new ResponseEntity<>(new Event("Heading required", "Message required"), HttpStatus.BAD_REQUEST);
		}

		eventHandler.addEvent(event);

		return new ResponseEntity<>(event, HttpStatus.OK);
	}


}
