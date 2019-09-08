package com.assignment.microservices.bank.beans;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BankAccountBean {

	private String id;
	
	@NotNull(message="Account Number cannot be empty")
	@Size(min=10,message = "Account Number should be at least 6 digits" )
	private String accountNo;
	private String name;
	private String lastName;
	private BigDecimal balance;

	public BankAccountBean() {
		super();
	}

	public BankAccountBean(String id, String accountNo, String name, String lastName, BigDecimal balance) {
		super();
		this.id = id;
		this.accountNo = accountNo;
		this.name = name;
		this.lastName = lastName;
		this.balance = balance;
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

	@Override
	public String toString() {
		return "BankAccountBean [id=" + id + ", accountNo=" + accountNo + ", name=" + name + ", lastName=" + lastName
				+ ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccountBean other = (BankAccountBean) obj;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
