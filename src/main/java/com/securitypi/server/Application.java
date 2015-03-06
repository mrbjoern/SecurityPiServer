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

        System.out.println("System started.");

        Readings readings = new Readings();

        SpringApplication.run(Application.class, args);
    }
}
