package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Deposit;
import reactor.core.publisher.Mono;

public interface IDeposittService {

	
	Mono<Account> send(Deposit deposit);
	
	Mono<Account> sendMyAccoun(Deposit deposit);
}
