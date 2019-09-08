package com.assignment.microservices.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin_logins")
public class LoginBean {

	@Id
	private String id;

	@NotNull(message = "You cant login without a username")
	@Column(unique = true)
	private String username;

	@NotNull(message = "You cant login without a password")
	private String password;

	public LoginBean() {
	}

	public LoginBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
