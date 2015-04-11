package com.securitypi.server;

import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Store temperature readings. As for prototyping, a static list should be sufficient,
 * but in production a database should preferable be used.
 */
@Repository
@Transactional
public class TemperatureReadingsHandler {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EventHandler eventHandler;

	private static final double MINTEMP = 15.0;
	private static final double MAXTEMP = 30.0;

	private static boolean temperatureWarningIssued;
	private static boolean temperatureBackToNormalIssued;

	public TemperatureReadingsHandler() {
		temperatureWarningIssued = false;
		temperatureBackToNormalIssued = true;
	}

	/**
	 * Add a temperature reading to be stored.
	 *
	 * @param temperatureReading TemperatureReading object to be stored
	 */
	public void addReading(TemperatureReading temperatureReading) {
		checkTemperature(temperatureReading);
		entityManager.persist(temperatureReading);
	}

	public TemperatureReading getLastReading() {
		try {
			String hql = "FROM TemperatureReading ORDER BY timestamp desc";
			Query query = entityManager.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);

			TemperatureReading temperatureReading = (TemperatureReading) query.getSingleResult();

			return temperatureReading;
		} catch (NoResultException e) {
			// The database is currently empty. Returning an empty reading is okay.
			return new TemperatureReading();
		}

	}

	/**
	 * Get the last n number of temperature readings. If the number of readings
	 * are less than the number provided, all readings will be returned.
	 *
	 * @param number The number of readings to be returned
	 * @return A linked list of readings to be returned sorted by latest reading first.
	 */
	public List<TemperatureReading> getReadings(int number) {

		return new LinkedList<>();
	}

	/**
	 * Get the average temperature in a given amount of hours.
	 *
	 * @param hours Number of hours. 0 currently indicates 0 hours, IE no results.
	 * @return The average temperature.
	 */
	@SuppressWarnings("JpaQueryApiInspection")
	public double getAverageTemperatureLastHours(int hours) {
		List<TemperatureReading> readings;
		double totalTemp = 0.0;

		Timestamp current = new Timestamp(new java.util.Date().getTime());
		Timestamp sinceHours = new Timestamp(current.getTime()- (hours * 60 * 60 * 1000));

		String hql = "FROM TemperatureReading WHERE timestamp between :from and :to";
		Query query = entityManager.createQuery(hql)
				.setParameter("to", current)
				.setParameter("from", sinceHours);
		readings = query.getResultList();

		if(readings.size() == 0) {
			return totalTemp;
		}

		for (int i = 0; i < readings.size(); i++) {
			totalTemp += readings.get(i).getTemperature();;
		}

		totalTemp = totalTemp / readings.size();

		// Temperature sensors have a max precision on 3 decimals.
		return new BigDecimal(totalTemp).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}

	// TODO: Handling of events below should be handled in a better way.

	private void checkTemperature(TemperatureReading temperatureReading) {
		if (temperatureReading.getTemperature() < MINTEMP || temperatureReading.getTemperature() > MAXTEMP) {
			setWarningEvent(temperatureReading);
			temperatureBackToNormalIssued = false;
		} else {
			// If the temperature is within the given threshold, knowledge of previous warning events should be cleared.
			setBackToNormalEvent();
			temperatureWarningIssued = false;
		}
	}

	private void setWarningEvent(TemperatureReading temperatureReading) {
		if (!temperatureWarningIssued) {
			eventHandler.addEvent(new Event("Temperature outside given threshold", "The temperature measured " +
					"was outside the given threshold boundaries. Measured temperature was " +
					temperatureReading.getTemperature() + " degrees."));
			temperatureWarningIssued = true;
		}
	}

	private void setBackToNormalEvent() {
		if (!temperatureBackToNormalIssued) {
			eventHandler.addEvent(new Event("Temperature back to normal", "The measured temperature are within the " +
					"given threshold boundaries."));
			temperatureBackToNormalIssued = true;
		}

	}
}
