package com.securitypi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Main application class for the Spring project.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String args[]) throws Throwable {
        new TemperatureReadingsHandler();

        SpringApplication.run(Application.class, args);
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
	}
}
