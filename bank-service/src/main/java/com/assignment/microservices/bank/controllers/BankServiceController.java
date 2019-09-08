package com.assignment.microservices.bank.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.microservices.bank.beans.BankAccountBean;
import com.assignment.microservices.bank.proxy.AccountsProxy;

@RestController
public class BankServiceController {

	@Autowired
	private AccountsProxy accountsProxy;

	@RequestMapping(method = RequestMethod.GET, path = "/accounts")
	public ResponseEntity<List<BankAccountBean>> listAccounts() {

		ResponseEntity<List<BankAccountBean>> accounts = accountsProxy.listAccounts();

		return ResponseEntity.status(accounts.getStatusCode()).body(accounts.getBody());

	}

	@RequestMapping(method = RequestMethod.GET, path = "/accounts/from/{from}/pagesize/{pagesize}")
	public ResponseEntity<List<BankAccountBean>> listAccountsByPage(@PathVariable int from,
			@PathVariable int pagesize) {

		ResponseEntity<List<BankAccountBean>> accounts = accountsProxy.listAccounts();

		if (from > accounts.getBody().size())
			return ResponseEntity.status(accounts.getStatusCode()).body(new ArrayList<>());
		else if ((from + pagesize) > accounts.getBody().size())
			return ResponseEntity.status(accounts.getStatusCode())
					.body(accounts.getBody().subList(from, accounts.getBody().size()));
		else
			return ResponseEntity.status(accounts.getStatusCode())
					.body(accounts.getBody().subList(from, from + pagesize));

	}

	@RequestMapping(method = RequestMethod.GET, path = "/accounts/{accountNo}")
	public ResponseEntity<BankAccountBean> getAccountDetailsByAccountNo(@PathVariable String accountNo) {

		ResponseEntity<BankAccountBean> bankAccountBean = accountsProxy.getAccountDetails(accountNo);

		return ResponseEntity.status(bankAccountBean.getStatusCode()).body(bankAccountBean.getBody());

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/accounts")
	public ResponseEntity<BankAccountBean> addNewAccount(@Valid @RequestBody BankAccountBean bankAccountBean) {

		ResponseEntity<BankAccountBean> responseEntity = accountsProxy.createAccount(bankAccountBean);

		return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

	}

	@RequestMapping(method = RequestMethod.POST, path = "/accounts")
	public ResponseEntity<BankAccountBean> updateAccount(@Valid @RequestBody BankAccountBean bankAccountBean) {

		ResponseEntity<BankAccountBean> responseEntity = accountsProxy.updateAccount(bankAccountBean);

		return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
	}

}
