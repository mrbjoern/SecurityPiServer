package com.securitypi.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Reporting controller for SecurityPi Server. Handles reporting of events from SecirutyPi app or other devices.
 */
@RequestMapping("/api/report/**")
@RestController
public class ReportController {

    /*@RequestMapping("/")
    public String test() {
        return "index";
    }*/

    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@RequestBody Map<String, String> body) {

        TemperatureReading tr = new TemperatureReading();

        for(Map.Entry<String, String> entry : body.entrySet()) {
            if(entry.getKey().equals("temperature")) {
                tr.setTemperature(Double.parseDouble(entry.getValue()));
            }
            else if(entry.getKey().equals("timestamp")) {
                tr.setTimestamp(entry.getValue());
            }
        }

        Readings.addReading(tr);

        return new ResponseEntity<TemperatureReading>(tr, HttpStatus.OK);
    }

     @RequestMapping(value = "/temperature", method = {RequestMethod.GET}, produces = {"application/json"})
    public TemperatureReading getReading() {
         return Readings.getLastReading();
     }
}
