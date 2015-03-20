package com.securitypi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// TODO: Access to /api is currently unrestricted. Should be fixed with either IP restrictions or API key.

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Currently just disable csrf, as this protection is not needed atm.
		// TODO: Find better alternative.
		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/", "/static/**", "/webjars/**", "/api/**").permitAll()
				.anyRequest()
				.authenticated();
		http.formLogin()
				.failureUrl("/login?error")
				.defaultSuccessUrl("/", true)
				.loginPage("/login")
				.permitAll()
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// TODO: Set up database connection here.

		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

}

