package com.assignment.microservices.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	Environment environment;

	public AuthorizationFilter(AuthenticationManager authManager, Environment environment) {
		super(authManager);
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

		if (authorizationHeader == null) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(environment.getProperty("authorization.token.header.name"));

		if (token == null) {
			return null;
		}

		String userId = Jwts.parser().setSigningKey(environment.getProperty("jwt.authentication.token")).parseClaimsJws(token)
				.getBody().getSubject();

		if (userId == null) {
			return null;
		}

		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

	}
}
