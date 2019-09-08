package com.assignment.microservices.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.assignment.microservices.beans.LoginBean;
import com.assignment.microservices.repository.LoginsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	

	private Environment environment;
	private LoginsRepository loginsRepository;

	public AuthenticationFilter(Environment environment,
			LoginsRepository loginsRepository) {
		super();
		this.environment = environment;
		this.loginsRepository = loginsRepository;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginBean loginBean = new ObjectMapper().readValue(request.getInputStream(), LoginBean.class);

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginBean.getUsername(), loginBean.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.attemptAuthentication(request, response);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();
		LoginBean loginBean = loginsRepository.findByUsername(username);
		
		String jwtToken = Jwts.builder().setSubject(loginBean.getId()).setExpiration(new Date(System.currentTimeMillis()+1000000)).
				signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.authentication.token")).compact();
		
		response.addHeader("token", jwtToken);
		response.addHeader("userId", username);
	}

}