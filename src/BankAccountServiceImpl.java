package com.capgemini.bankapp.service.impl;

import java.util.List;



import com.capgemini.bankapp.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.bankapp.dao.impl.BankAccountDaoImplUsingJdbcTemplate;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
//import com.capgemini.bankapp.util.DbUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.*;

@Transactional
public class BankAccountServiceImpl implements BankAccountService {
	

	private BankAccountDao bankAccountdao;
	public BankAccountServiceImpl(BankAccountDao bankAccountdao){
		this.bankAccountdao = bankAccountdao;
	}

	//public BankAccountServiceImpl() {
	//	bankAccountdao = new BankAccountDaoImpl();
	//}

	@Override
	public double checkBalance(long accountId) throws BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance >= 0)
			return balance;
		throw new BankAccountNotFoundException("Bank account doesnt exist");
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		else if(balance - amount >= 0) {
			balance = balance - amount;
			bankAccountdao.updateBalance(accountId, balance);
			return balance;
		}else 
			throw new LowBalanceException("You dont have sufficient fund");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		balance = balance + amount;
		bankAccountdao.updateBalance(accountId, balance);
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {
		boolean result = bankAccountdao.deleteBankAccount(accountId);
		if(result) {
			return result;
		}else{
			throw new BankAccountNotFoundException("BankAccount doesn't exist");
		}	
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result =  bankAccountdao.addNewBankAccount(account);
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountdao.findAllbankAccounts();
	}

	@Override
	@Transactional(rollbackFor=BankAccountNotFoundException.class)
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException, BankAccountNotFoundException {
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			return newBalance;
		}
		catch(LowBalanceException | BankAccountNotFoundException |  EmptyResultDataAccessException e) {			
			System.out.println("exception in service");
			//e.printStackTrace();
		   	throw e;
		}
	
	}

	private double withdrawForFundTransfer(long accountId, double amount)throws BankAccountNotFoundException, LowBalanceException {
		double balance = bankAccountdao.getBalance(accountId);
		if(balance < 0)
			throw new BankAccountNotFoundException("BankAccount doesnt exist");
		else if(balance - amount >= 0) {
			balance = balance - amount;
			bankAccountdao.updateBalance(accountId, balance);
			return balance;
		}else 
			throw new LowBalanceException("You dont have sufficient fund");
	}

	@Override
	public BankAccount searchAccountDetails(long accountId) throws BankAccountNotFoundException 	{
		BankAccount account = bankAccountdao.searchAccountDetails(accountId);
		if(account != null)
			return account;
		throw new BankAccountNotFoundException("BankAccount doesn't exist");
	}

	@Override
	public boolean updateBankAccount(long accountId, String accountHolderName, String accountType)throws BankAccountNotFoundException {
		boolean result= bankAccountdao.updateBankAccount(accountId, accountHolderName, accountType);
		return result;
		
	}	
	

}
