package com.assignment.microservices.bank.proxy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assignment.microservices.bank.beans.BankAccountBean;

@FeignClient(name = "accounts-service", fallback = AccountsFallback.class)
@RibbonClient(name = "accounts-service")
public interface AccountsProxy {

	@GetMapping("/accounts/{accountNo}")
	public ResponseEntity<BankAccountBean> getAccountDetails(@PathVariable(name = "accountNo") String accountNo);

	@GetMapping("/accounts/")
	public ResponseEntity<List<BankAccountBean>> listAccounts();

	@PutMapping("/accounts/")
	public ResponseEntity<BankAccountBean> createAccount(@RequestBody BankAccountBean bankAccountBean);

	@PostMapping("/accounts/")
	public ResponseEntity<BankAccountBean> updateAccount(@RequestBody BankAccountBean bankAccountBean);

}

@Component
class AccountsFallback implements AccountsProxy {

	@Override
	public ResponseEntity<BankAccountBean> getAccountDetails(String accountNo) {
		BankAccountBean bankAccountBean = new BankAccountBean();
		bankAccountBean.setAccountNo(accountNo);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bankAccountBean);
	}

	@Override
	public ResponseEntity<List<BankAccountBean>> listAccounts() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<BankAccountBean>());
	}

	@Override
	public ResponseEntity<BankAccountBean> createAccount(BankAccountBean bankAccountBean) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bankAccountBean);
	}

	@Override
	public ResponseEntity<BankAccountBean> updateAccount(BankAccountBean bankAccountBean) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bankAccountBean);
	}

}