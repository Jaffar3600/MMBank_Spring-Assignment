/*
 * package com.cg.app.account.dao;
 * 
 * import java.sql.Connection; import java.sql.PreparedStatement; import
 * java.sql.ResultSet; import java.sql.SQLException; import java.sql.Statement;
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.context.annotation.Primary; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.cg.app.account.SavingsAccount; import
 * com.cg.app.account.util.DBUtil; import
 * com.cg.app.exception.AccountNotFoundException;
 * 
 * public class SavingsAccountDAOImpl implements SavingsAccountDAO {
 * 
 * public SavingsAccount createNewAccount(SavingsAccount account) throws
 * ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
 * preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
 * preparedStatement.setString(2,
 * account.getBankAccount().getAccountHolderName());
 * preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
 * preparedStatement.setBoolean(4, account.isSalary());
 * preparedStatement.setObject(5, null); preparedStatement.setString(6, "SA");
 * preparedStatement.executeUpdate(); preparedStatement.close();
 * DBUtil.commit(); return account; }
 * 
 * public List<SavingsAccount> getAllSavingsAccount() throws
 * ClassNotFoundException, SQLException { List<SavingsAccount> savingsAccounts =
 * new ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT"); while (resultSet.next()) {//
 * Check if row(s) is present in table int accountNumber = resultSet.getInt(1);
 * String accountHolderName = resultSet.getString("account_hn"); double
 * accountBalance = resultSet.getDouble(3); boolean salary =
 * resultSet.getBoolean("salary"); SavingsAccount savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } DBUtil.commit(); return
 * savingsAccounts; }
 * 
 * @Override public void updateBalance(int accountNumber, double currentBalance)
 * throws ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); connection.setAutoCommit(false); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("UPDATE ACCOUNT SET account_bal=? where account_id=?");
 * preparedStatement.setDouble(1, currentBalance); preparedStatement.setInt(2,
 * accountNumber); preparedStatement.executeUpdate(); }
 * 
 * @Override public SavingsAccount getAccountById(int accountNumber) throws
 * ClassNotFoundException, SQLException, AccountNotFoundException { Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement ("SELECT * FROM account where account_id=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultSet =
 * preparedStatement.executeQuery(); SavingsAccount savingsAccount = null;
 * if(resultSet.next()) { String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * savingsAccount = new SavingsAccount(accountNumber, accountHolderName,
 * accountBalance, salary); return savingsAccount; } throw new
 * AccountNotFoundException("Account with account number "
 * +accountNumber+" does not exist."); }
 * 
 * @Override public SavingsAccount deleteAccount(int accountNumber) throws
 * ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement ("delete From account where account_id=?");
 * preparedStatement.setInt(1, accountNumber); preparedStatement.execute();
 * DBUtil.commit(); return null; }
 * 
 * 
 * @Override public void commit() throws SQLException { DBUtil.commit(); }
 * 
 * @Override public double checkCurrentBalance(int accountNumber) throws
 * ClassNotFoundException, SQLException, AccountNotFoundException { Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement
 * ("select account_bal from account where account_id=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultset =
 * preparedStatement.executeQuery(); double accountBalance = 0;
 * if(resultset.next()){ accountBalance = resultset.getDouble(1); return
 * accountBalance; } throw new AccountNotFoundException("invalid input"); }
 * 
 * @Override public SavingsAccount seachByAccountNumber(int accountNumber)
 * throws SQLException, ClassNotFoundException, AccountNotFoundException {
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("select * from account where account_id=?"); preparedStatement.setInt(1,
 * accountNumber); ResultSet resultset = preparedStatement.executeQuery();
 * if(resultset.next()){ String accountHolderName =
 * resultset.getString("account_hn"); double accountBalance =
 * resultset.getDouble("account_bal"); boolean salary = resultset.getBoolean(4);
 * SavingsAccount savingaccount = new
 * SavingsAccount(accountNumber,accountHolderName,accountBalance, salary);
 * return savingaccount; } throw new AccountNotFoundException("invalid input");
 * }
 * 
 * @Override public SavingsAccount seachByAccountName(String accountHolderName)
 * throws SQLException, ClassNotFoundException, AccountNotFoundException {
 * 
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("select * from account where account_hn=?"); preparedStatement.setString(1,
 * accountHolderName); ResultSet resultset = preparedStatement.executeQuery();
 * if(resultset.next()){ int accountNumber = resultset.getInt("account_id");
 * double accountBalance = resultset.getDouble("account_bal"); boolean salary =
 * resultset.getBoolean(4); SavingsAccount savingaccount = new
 * SavingsAccount(accountNumber,accountHolderName,accountBalance, salary);
 * return savingaccount; } throw new AccountNotFoundException("invalid input");
 * }
 * 
 * @Override public SavingsAccount updateAccount(String newAccountHolderName,int
 * accountNumber,boolean salary) throws SQLException, ClassNotFoundException {
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("update account set account_hn=?,salary=? where account_id=?");
 * preparedStatement.setString(1, newAccountHolderName);
 * preparedStatement.setBoolean(2, salary); preparedStatement.setInt(3,
 * accountNumber); preparedStatement.executeUpdate(); return null; }
 * 
 * 
 * public List<SavingsAccount> sortByaccountHolderName() throws
 * ClassNotFoundException, SQLException { List<SavingsAccount> savingsAccounts =
 * new ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_hn"); while
 * (resultSet.next()) {// Check if row(s) is present in table int accountNumber
 * = resultSet.getInt(1); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
 * accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } DBUtil.commit(); return
 * savingsAccounts; }
 * 
 * @Override public void sortByName(int accountNumber, String accountHolderName)
 * throws ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); Statement statement = connection.createStatement();
 * statement.executeQuery("SELECT * FROM ACCOUNT order by account_hn");
 * DBUtil.commit();
 * 
 * }
 * 
 * @Override public List<SavingsAccount> sortByaccountBalance() throws
 * ClassNotFoundException, SQLException { List<SavingsAccount> savingsAccounts =
 * new ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_bal"); while
 * (resultSet.next()) {// Check if row(s) is present in table int accountNumber
 * = resultSet.getInt(1); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
 * accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } DBUtil.commit(); return
 * savingsAccounts;
 * 
 * }
 * 
 * @Override public List<SavingsAccount> sortByaccountNumber() throws
 * ClassNotFoundException, SQLException { List<SavingsAccount> savingsAccounts =
 * new ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_id"); while
 * (resultSet.next()) {// Check if row(s) is present in table int accountNumber
 * = resultSet.getInt(1); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
 * accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } DBUtil.commit(); return
 * savingsAccounts;
 * 
 * }
 * 
 * 
 * }
 */