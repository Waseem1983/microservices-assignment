package com.assignment.microservices.repository;

import org.springframework.data.repository.CrudRepository;

import com.assignment.microservices.beans.LoginBean;

public interface LoginsRepository extends CrudRepository<LoginBean, String> { 
	
	LoginBean findByUsername(String username);

}