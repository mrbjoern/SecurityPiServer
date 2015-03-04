package com.securitypi.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Reporting controller for SecurityPi Server. Handles reporting of events from SecirutyPi app or other devices.
 */
@RequestMapping("/api/report/**")
@RestController
public class ReportController {

    @RequestMapping("/")
    public String test() {
        return "index";
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@RequestBody TemperatureReading tr) {

        Readings.addReading(tr);

        return new ResponseEntity<>(tr, HttpStatus.OK);
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.GET}, produces = {"application/json"})
    public TemperatureReading getReading() {
         return Readings.getLastReading();
     }
}
