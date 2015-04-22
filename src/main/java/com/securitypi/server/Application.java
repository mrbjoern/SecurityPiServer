package com.securitypi.server;

import com.securitypi.server.api.ApiTokenHandler;
import com.securitypi.server.events.EventHandler;
import com.securitypi.server.temperatures.TemperatureReadingsHandler;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.FileNotFoundException;

/**
 * Main application class for the Spring project.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.securitypi"})
@EnableJpaRepositories
@EntityScan
public class Application extends WebMvcConfigurerAdapter {

	@Value("${keystore.file}")
	private String keystoreFile;
	@Value("${keystore.pass}")
	private String keystorePass;

	@Autowired
	private EventHandler eventHandler;

	public static void main(String args[]) throws Throwable {

		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() throws FileNotFoundException {
		final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer factory) {
				if (factory instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
					containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
						@Override
						public void customize(Connector connector) {
							connector.setSecure(true);
							connector.setScheme("https");

							// For production only.
							//connector.setProxyName("secpi.mrbjoern.com");
							//connector.setProxyPort(443);

							Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
							proto.setSSLEnabled(true);
							proto.setKeystoreFile(absoluteKeystoreFile);
							proto.setKeystorePass(keystorePass);
							proto.setKeystoreType("PKCS12");
							proto.setKeyAlias("tomcat");
						}
					});
				}
			}
		};
	}
}
