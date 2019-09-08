package com.assignment.microservices.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.assignment.microservices.repository.LoginsRepository;
import com.assignment.microservices.security.filter.AuthenticationFilter;
import com.assignment.microservices.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private AuthenticationService authenticationService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private Environment environment;

	private LoginsRepository loginsRepository;

	@Autowired
	public WebSecurity(AuthenticationService authenticationService, BCryptPasswordEncoder bCryptPasswordEncoder,
			Environment environment, LoginsRepository loginsRepository) {
		super();
		this.authenticationService = authenticationService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
		this.loginsRepository = loginsRepository;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
		httpSecurity.headers().frameOptions().disable();

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(environment, loginsRepository);
		authenticationFilter.setAuthenticationManager(authenticationManager());

		httpSecurity.addFilter(authenticationFilter);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(authenticationService).passwordEncoder(bCryptPasswordEncoder);
	}

}
