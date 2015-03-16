package com.securitypi.server.api;

import com.securitypi.server.SecurityPi;
import com.securitypi.server.SecurityPiHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/connect/**")
@RestController
public class ConnectionController {
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
}
