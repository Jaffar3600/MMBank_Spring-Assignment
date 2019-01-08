package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.AccountNotFoundException;

@Repository
@Primary
public class SavingsAccountSJDAOImpl implements SavingsAccountDAO {
	@Autowired
	private JdbcTemplate jdbctemplate;

	@Override
	public SavingsAccount createNewAccount(SavingsAccount account)  {
		jdbctemplate.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",new Object[] {
				account.getBankAccount().getAccountNumber(),
				account.getBankAccount().getAccountHolderName(),
				account.getBankAccount().getAccountBalance(),
				account.isSalary(),
				null,
				"SA"
		});
		return account;
	}

	@Override
	public SavingsAccount getAccountById(int accounctNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
	
		return (SavingsAccount) jdbctemplate.queryForObject("SELECT * from account WHERE account_id=?",new Object[] {accounctNumber} , new SavingAccountMap());
	
	}
	

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		jdbctemplate.update("delete from account where  account_id =?", accountNumber);
		return null;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount()  {
	List<SavingsAccount> list = jdbctemplate.query("select * from account", new SavingAccountMap());
		return list;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance)  {
		jdbctemplate.update("UPDATE ACCOUNT SET account_bal=? where account_id=?", new Object[] {currentBalance,accountNumber});

	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public double checkCurrentBalance(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		
		
	return  jdbctemplate.queryForObject("select account_bal from account where account_id=?",new Object[] {accountNumber}, Double.class);
	
	}

	@Override
	public SavingsAccount seachByAccountNumber(int accountNumber)
			throws AccountNotFoundException {
		return (SavingsAccount) jdbctemplate.queryForList("SELECT * from account WHERE id=?",accountNumber);
		
	}

	@Override
	public SavingsAccount seachByAccountName(String accountHolderName)
			throws AccountNotFoundException {
		return	(SavingsAccount) jdbctemplate.queryForMap("select * from account where account_hn=?",accountHolderName, new SavingAccountMap());
		 
	}

	@Override
	public SavingsAccount updateAccount(String newAccountHolderName, int accountNumber, boolean salary)
			throws AccountNotFoundException, ClassNotFoundException, SQLException {
		SavingsAccount savingaccount = getAccountById(accountNumber);
		System.out.println("update");
		 jdbctemplate.update("update account set account_hn=?,salary=? where account_id=?", new Object[] {newAccountHolderName,salary,accountNumber} );
		return savingaccount;
	}

	@Override
	public void sortByName(int accountNumber, String accountHolderName) throws ClassNotFoundException, SQLException {
		

	}

	@Override
	public List<SavingsAccount> sortByaccountHolderName() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = jdbctemplate.query("select * from account order by account_hn",new SavingAccountMap());
		return list;
	}

	@Override
	public List<SavingsAccount> sortByaccountBalance() throws ClassNotFoundException, SQLException {
		 List<SavingsAccount> list =jdbctemplate.query("SELECT * FROM ACCOUNT ORDER BY account_bal",new SavingAccountMap());
		return list;
	}

	@Override
	public List<SavingsAccount> sortByaccountNumber() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = jdbctemplate.query("select * from account order by account_id", new SavingAccountMap());
		return list;
	}
}
