package com.securitypi.server.securitypi;


import com.securitypi.server.api.ApiToken;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "security_pies")
public class SecurityPi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	private ApiToken token;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Connection> connections = new LinkedList<>();

	private int reportInterval;

	public void setToken(ApiToken token) {
		this.token = token;
	}

	public ApiToken getToken() {
		return token;
	}

	public long getId() {
		return id;
	}

	public List<Connection> getConnections() {
		return connections;
	}

}
