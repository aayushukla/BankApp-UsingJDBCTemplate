package com.capgemini.bankapp.dao.rowmapper;

import org.springframework.jdbc.core.*;
import com.capgemini.bankapp.model.BankAccount;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountRowMapper implements RowMapper<BankAccount>{

	public BankAccount mapRow(ResultSet rs, int rowNum)throws SQLException{
		long accountId=rs.getLong(1);
		String accountHolderName=rs.getString(2);
		String accountType = rs.getString(3);
		double accountBalance = rs.getDouble(4);
		BankAccount account = new BankAccount(accountId, accountHolderName, 		accountType, accountBalance);
		return account;
	
	}


}