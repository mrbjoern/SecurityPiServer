package com.securitypi.server.securitypi;


import com.securitypi.server.api.ApiToken;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "security_pies")
public class SecurityPi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	private ApiToken token;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Connection> connections = new HashSet<>(0);

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
}
