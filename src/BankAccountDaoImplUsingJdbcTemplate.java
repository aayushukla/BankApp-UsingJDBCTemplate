package com.capgemini.bankapp.bankapp.dao.impl;

import com.capgemini.bankapp.dao.rowmapper.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.model.BankAccount;
//import com.capgemini.bankapp.util.DbUtil;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.*;

@Transactional
public class BankAccountDaoImplUsingJdbcTemplate implements BankAccountDao{

	JdbcTemplate jdbcTemplate;
	public BankAccountDaoImplUsingJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public double getBalance(long accountId){
		String query = "SELECT account_balance FROM bankaccounts WHERE account_id = " + accountId;
		double balance;
		balance=jdbcTemplate.queryForObject(query,Double.class);
		return balance;
	}

	@Override
	public void updateBalance(long accountId, double newBalance){
		String query="UPDATE bankaccounts SET account_balance='"+newBalance+"' WHERE account_id='"+accountId+"' ";

		jdbcTemplate.update(query);
	}

	@Override
	public boolean deleteBankAccount(long accountId){
		String query = "DELETE FROM bankaccounts WHERE account_id ="+accountId;
		int result = jdbcTemplate.update(query);
		if(result == 1){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		String query = "INSERT INTO bankaccounts (customer_name, account_type, account_balance)VALUES ('"+account.getAccountHolderName()+"','"+account.getAccountType()+"','"+account.getAccountBalance()+"')";
		int result = jdbcTemplate.update(query);
		if(result > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<BankAccount> findAllbankAccounts() {
		List<BankAccount> accounts = jdbcTemplate.query("SELECT * FROM bankaccounts",new BankAccountRowMapper());
		return accounts;
	}

	@Override
	public BankAccount searchAccountDetails(long accountId) {
		String query = "SELECT * FROM bankaccounts WHERE account_id = " + accountId;
		BankAccount account= new BankAccount();
		try {
			account=jdbcTemplate.queryForObject(query,new BankAccountRowMapper());
		}
		catch(EmptyResultDataAccessException e) {
			//e.printStackTrace(); 
			System.out.println("Exception in Dao");
		}	
		return account;
		
	}

	@Override
	public boolean updateBankAccount(long accountId, String accountHolderName, String accountType) {
		String query = "UPDATE bankaccounts SET customer_name='"+accountHolderName+"',account_type='"+accountType+"' WHERE account_id='"+accountId+"' ";
		int result = jdbcTemplate.update(query);
		if (result == 1)
			return true;
		else
			return false;
		
	}


}