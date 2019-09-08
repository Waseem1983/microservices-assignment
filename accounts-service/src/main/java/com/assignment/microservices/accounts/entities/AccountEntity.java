package com.assignment.microservices.accounts.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountEntity {

	@Id
	private String id;

	@Column(name = "account_no", nullable = false)
	private String accountNo;

	@Column(name = "name")
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "balance")
	private BigDecimal balance;

	private int port;

	public AccountEntity() {
	}

	public AccountEntity(String id, String accountNo, String name, String lastName, BigDecimal balance, int port) {
		super();
		this.id = id;
		this.accountNo = accountNo;
		this.name = name;
		this.lastName = lastName;
		this.balance = balance;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
