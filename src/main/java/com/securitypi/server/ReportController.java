package com.securitypi.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Reporting controller for SecurityPi Server. Handles reporting of events from SecirutyPi app or other devices.
 */
@RequestMapping("/api/report/**")
@RestController
public class ReportController {

	// Currently, an empty JSON body is supported, resulting in TemperatureReading objects with default temperature 0.0
	// TODO: Implement handling for empty temperature reading objects.
    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@RequestBody TemperatureReading currentReading) {

		// As default, timestamp should not be set by sender, but handled by server.
		if(currentReading.getTimestamp() == null) {
			currentReading.setTimestamp(new TimestampWrapper().getFormatedTimestamp());
		}

        TemperatureReadingsHandler.addReading(currentReading);

        return new ResponseEntity<>(currentReading, HttpStatus.OK);
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.GET}, produces = {"application/json"})
    public TemperatureReading getReading() {
         return TemperatureReadingsHandler.getLastReading();
    }
}
