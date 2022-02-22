package com.bank.servicedb;

import org.springframework.stereotype.Service;
import com.bank.model.Account;
import com.bank.model.Cards;
import com.bank.model.Deposit;
import com.bank.service.IDeposittService;
import com.bank.webclient.repoWebClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DepositServiceDB implements IDeposittService{
	
	private repoWebClient repoweb=new repoWebClient();

	
	@Override
	public Mono<Account> send(Deposit deposit) {
		
		Mono<Account> accountSubmit=repoweb.FindByAccountNumber(deposit.getNumsubmit());
		Mono<Account> accountReceive=repoweb.FindByAccountNumber(deposit.getNumreceive());
		
		
		return accountSubmit.doOnNext(as->{
			if(as.getAmmount()< deposit.getAmmount())
				throw new RuntimeException("Usted no cuenta con el saldo disponible para el envio");
		}).flatMap(as->{
			return accountReceive.flatMap(ar->{
				
				as.setAmmount(as.getAmmount()-deposit.getAmmount());
				ar.setAmmount(ar.getAmmount()+deposit.getAmmount());
				
				repoweb.updateAccount(as.getId(), as).subscribe();
				
				
				return repoweb.updateAccount(ar.getId(), ar);
			});
		});
	}
	
	@Override
	public Mono<Account> sendMyAccoun(Deposit deposit) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
