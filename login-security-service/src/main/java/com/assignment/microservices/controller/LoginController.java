package com.assignment.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.microservices.beans.LoginBean;
import com.assignment.microservices.services.AuthenticationService;

@RestController
public class LoginController {

	private AuthenticationService authenticationService;

	@Autowired
	public LoginController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/signup")
	public ResponseEntity<LoginBean> createUpdateAdminAccount(@RequestBody LoginBean loginBean) {

		authenticationService.createUser(loginBean);
		return ResponseEntity.status(HttpStatus.CREATED).body(loginBean);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<LoginBean> login(@RequestBody LoginBean loginBean) {

		UserDetails userDetails = authenticationService.loadUserByUsername(loginBean.getUsername());
		loginBean.setPassword(userDetails.getPassword());
		return ResponseEntity.status(HttpStatus.OK).body(loginBean);
	}

}
