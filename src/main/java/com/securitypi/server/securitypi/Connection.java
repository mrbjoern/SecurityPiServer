package com.securitypi.server.securitypi;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "connections")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private Timestamp timestamp;

	private String ipAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private SecurityPi securityPi;

	private Connection() {
	}

	public Connection(SecurityPi securityPi, String ipAddress) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.ipAddress = ipAddress;
		this.securityPi = securityPi;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}
}
