package com.bank.servicedb;

import java.util.Date;

import org.springframework.stereotype.Service;
import com.bank.model.Account;
import com.bank.model.Cards;
import com.bank.model.Deposit;
import com.bank.model.Movement;
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
				
				Movement movement=new Movement();
				Movement move=new Movement();
				movement.setAmmount(deposit.getAmmount());
				movement.setCommissioncharged(0.0);
				movement.setIdproduct(as.getIdproduct());
				movement.setIdcustomer(as.getIdclient());
				movement.setTypemovement("deposit");
				movement.setNameproduct(as.getNameproduct());
				movement.setDate(new Date());
				ar.setAmmount(ar.getAmmount()+deposit.getAmmount());
				move.setAmmount(deposit.getAmmount());
				if(as.getMaxmovements()==0) {
					deposit.setAmmount(deposit.getAmmount()+1);
					movement.setCommissioncharged(1.0);
				}
				if (as.getMaxmovements() >= 1) {
					as.setMaxmovements(as.getMaxmovements()-1);
	            }	
				Mono<Movement> depositMovement= repoweb.saveMovement(movement);
				
				as.setAmmount(as.getAmmount()-deposit.getAmmount());
				move.setCommissioncharged(0.0);
				move.setIdproduct(ar.getIdproduct());
				move.setNameproduct(ar.getNameproduct());
				move.setIdcustomer(ar.getIdclient());
				move.setTypemovement("received");
				move.setDate(new Date());
				Mono<Movement> receivedMovement= repoweb.saveMovement(move);
				
				receivedMovement.subscribe();
				depositMovement.subscribe();
				repoweb.updateAccount(as.getId(), as).subscribe();
				return repoweb.updateAccount(ar.getId(), ar);
			});
		});
	}
	
	private Mono<Movement> savemovement(Account as) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Mono<Account> sendMyAccoun(Deposit deposit) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
