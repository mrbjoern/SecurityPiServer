package com.securitypi.server;

import com.securitypi.server.events.Event;
import com.securitypi.server.events.EventHandler;
import com.securitypi.server.events.SystemStartedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;
import java.util.Properties;

/**
 * Main application class for the Spring project.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.securitypi"})
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String args[]) throws Throwable {
        new TemperatureReadingsHandler();
		new EventHandler();
		new SecurityPiHandler();

		EventHandler.addEvent(new SystemStartedEvent());

        SpringApplication.run(Application.class, args);
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties(){
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		Properties server = new Properties();

		server.setProperty("server.tomcat.remote_ip_header", "x-forwarded-for");
		server.setProperty("spring.thymeleaf.cache", "false");

		propertySourcesPlaceholderConfigurer.setProperties(server);

		return propertySourcesPlaceholderConfigurer;
	}
}
