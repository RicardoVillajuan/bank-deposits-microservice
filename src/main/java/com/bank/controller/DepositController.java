package com.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.bank.model.Account;
import com.bank.model.Deposit;
import com.bank.service.IDeposittService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/deposits")
public class DepositController {

	private final IDeposittService deposittService;
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Account> save(@RequestBody Deposit deposit){
		
		return deposittService.send(deposit);
	}
	
	
	@PostMapping("/myaccount")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Account> saveDepositAccount(@RequestBody Deposit deposit){
		
		return deposittService.sendMyAccoun(deposit);
	}
}
