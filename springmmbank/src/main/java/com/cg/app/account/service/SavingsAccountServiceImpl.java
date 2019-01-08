package com.cg.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.dao.SavingsAccountDAO;
import com.cg.app.account.factory.AccountFactory;
import com.cg.app.exception.AccountNotFoundException;
import com.cg.app.exception.InsufficientFundsException;
import com.cg.app.exception.InvalidInputException;

@Service
@Transactional
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl() {
		
		factory = AccountFactory.getInstance();

	}

	@Override
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		System.out.println(accountHolderName+" "+ accountBalance+" "+  salary);
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		
		savingsAccountDAO.createNewAccount(account);
		return null;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	@Override
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			// savingsAccountDAO.commit();
		} else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}

	@Override
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance >= amount) {
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			// savingsAccountDAO.commit();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
	}
	

	@Override
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		
			deposit(receiver, amount);
			withdraw(sender, amount);
			
		
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {

		return savingsAccountDAO.deleteAccount(accountNumber);
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount account) {

		return null;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public double checkBalance(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.checkCurrentBalance(accountNumber);

	}

	@Override
	public SavingsAccount seachByAccountNumber(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {

		return savingsAccountDAO.seachByAccountNumber(accountNumber);

	}

	@Override
	public SavingsAccount AccountHolderName(String accountHolderName)
			throws SQLException, ClassNotFoundException, AccountNotFoundException {

		return savingsAccountDAO.seachByAccountName(accountHolderName);
	}

	@Override
	public SavingsAccount updateAccount(String newAccountHolderName, int accountNumber, boolean salary)
			throws SQLException, ClassNotFoundException, AccountNotFoundException {

		return savingsAccountDAO.updateAccount(newAccountHolderName, accountNumber, salary);
	}

	@Override
	public void sortByName(int accountNumber, String accountHolderName) throws ClassNotFoundException, SQLException {
		savingsAccountDAO.sortByName(accountNumber, accountHolderName);
	}

	@Override
	public List<SavingsAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return savingsAccountDAO.sortByaccountHolderName();
	}

	@Override
	public List<SavingsAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException {

		return savingsAccountDAO.sortByaccountBalance();
	}

	@Override
	public List<SavingsAccount> sortByAccountNumber() throws ClassNotFoundException, SQLException {

		return savingsAccountDAO.sortByaccountNumber();
	}

}
