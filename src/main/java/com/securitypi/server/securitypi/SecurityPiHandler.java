package com.securitypi.server.securitypi;

import com.securitypi.server.api.ApiToken;
import com.securitypi.server.api.ApiTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;


@Repository
@Transactional
public class SecurityPiHandler {

	@Autowired
	private ApiTokenHandler apiTokenHandler;

	@PersistenceContext
	private EntityManager entityManager;

	public void addNewSecurityPiComponent(SecurityPi securityPi) {
		entityManager.persist(securityPi);
	}

	public void addConnection(Connection connection) {
		entityManager.persist(connection);
	}

	public SecurityPi findSecurityPiByToken(String token) {
		ApiToken apiToken = apiTokenHandler.getApiToken(token);

		SecurityPi securityPi = (SecurityPi) entityManager.createQuery("from SecurityPi where token_id = :tokenId")
				.setParameter("tokenId", apiToken.getId())
				.getSingleResult();

		if(securityPi == null) {
			return new SecurityPi();
		}
		else {
			return securityPi;
		}
	}

	public boolean securityPyBelongsToToken(String token) {
		if(findSecurityPiByToken(token) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<SecurityPi> getAllSecurityPies() {
		List<SecurityPi> allPies = entityManager.createQuery("from SecurityPi").getResultList();
		return allPies;
	}
}
