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

	/**
	 * Before any reporting can be done, the SecurityPi software must be registered and connected. SecurityPi Server
	 * should know the reporting interval and address of the Pi, so it can issue warnings when reportings are missing
	 * or ping back to check if the connection is lost.
	 * @param connectedSecurityPi
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.POST}, produces = {"application/json"})
	public ResponseEntity<SecurityPi> connectSecurityPi(@RequestBody SecurityPi connectedSecurityPi) {
		SecurityPiHandler.connectSecurityPi(connectedSecurityPi);
		return new ResponseEntity<SecurityPi>(connectedSecurityPi, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = {RequestMethod.GET}, produces ={"application/json"})
	public ResponseEntity<SecurityPi> connectSecurityPi() {
		SecurityPi spi = new SecurityPi();
		SecurityPiHandler.connectSecurityPi(spi);
		return new ResponseEntity<SecurityPi>(spi, HttpStatus.OK);
	}

    @RequestMapping(value = "/temperature", method = {RequestMethod.POST}, produces = {"application/json"})
    public ResponseEntity<TemperatureReading> addReading(@RequestBody TemperatureReading currentReading) {

        TemperatureReadingsHandler.addReading(currentReading);

        return new ResponseEntity<>(currentReading, HttpStatus.OK);
    }

    @RequestMapping(value = "/temperature", method = {RequestMethod.GET}, produces = {"application/json"})
    public TemperatureReading getReading() {
         return TemperatureReadingsHandler.getLastReading();
    }
}
