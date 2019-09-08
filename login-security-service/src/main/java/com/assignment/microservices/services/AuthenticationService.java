package com.assignment.microservices.services;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.microservices.beans.LoginBean;
import com.assignment.microservices.repository.LoginsRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	private LoginsRepository loginsRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public AuthenticationService(LoginsRepository loginsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.loginsRepository = loginsRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public LoginBean createUser(LoginBean loginBean) {

		loginBean.setId(UUID.randomUUID().toString());
		loginBean.setPassword(bCryptPasswordEncoder.encode(loginBean.getPassword()));
		loginsRepository.save(loginBean);

		return loginBean;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginBean loginBean = loginsRepository.findByUsername(username);
		if (loginBean == null) {
			throw new UsernameNotFoundException("User : " + username + " Does not exist");
		}
		User user = new User(username, loginBean.getPassword(), true, true, true, true, new ArrayList<>());
		return user;
	}

}
