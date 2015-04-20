package com.securitypi.server.temperatures;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Stores reading of temperatures with timestamp to be stored in database or other format.
 */
@Entity
@Table(name = "temperature_reading")
public class TemperatureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private double temperature;

    @NotNull
    private Timestamp timestamp;


    public TemperatureReading() {

    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
