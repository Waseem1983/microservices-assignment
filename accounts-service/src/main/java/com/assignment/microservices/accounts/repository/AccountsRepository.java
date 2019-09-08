package com.assignment.microservices.accounts.repository;

import org.springframework.data.repository.CrudRepository;

import com.assignment.microservices.accounts.entities.AccountEntity;

public interface AccountsRepository extends CrudRepository<AccountEntity, String> {

	AccountEntity findByAccountNo(String accountNo);
}
