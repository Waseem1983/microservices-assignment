package com.assignment.microservices.accounts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.microservices.accounts.entities.AccountEntity;
import com.assignment.microservices.accounts.repository.AccountsRepository;

import javassist.NotFoundException;

@RestController
public class AccountsController {

	@Autowired
	private AccountsRepository repository;

	@RequestMapping(method = RequestMethod.GET, path = "/accounts/{accountNo}")
	public ResponseEntity<AccountEntity> retrieveExchangeValue(@PathVariable("accountNo") String accountNo)
			throws NotFoundException {

		AccountEntity account = repository.findByAccountNo(accountNo);

		if (account == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(account);

		return ResponseEntity.status(HttpStatus.OK).body(account);

	}

	@RequestMapping(method = RequestMethod.GET, path = "/accounts")
	public ResponseEntity<List<AccountEntity>> retrieveAllAccounts() {

		List<AccountEntity> accounts = new ArrayList<>();
		repository.findAll().forEach(accounts::add);

		return ResponseEntity.status(HttpStatus.OK).body(accounts);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/accounts")
	public ResponseEntity<AccountEntity> updateAccount(@RequestBody AccountEntity account) {

		AccountEntity entity = repository.findByAccountNo(account.getAccountNo());

		if (entity == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(account);

		account.setId(entity.getId());
		repository.save(account);

		return ResponseEntity.status(HttpStatus.OK).body(account);

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/accounts")
	public ResponseEntity<AccountEntity> createAccount(@RequestBody AccountEntity account) {

		account.setId(UUID.randomUUID().toString());
		repository.save(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(account);

	}

}
