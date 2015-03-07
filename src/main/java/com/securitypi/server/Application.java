package com.securitypi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main application class for the Spring project.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {

    public static void main(String args[]) {
        new TemperatureReadingsHandler();

        SpringApplication.run(Application.class, args);
    }
}
