package com.cg.app.account.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.service.SavingsAccountService;

import com.cg.app.exception.AccountNotFoundException;
@Component
public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	@Autowired
	private SavingsAccountService savingsAccountService;
	public void start() throws ClassNotFoundException, SQLException {
		
		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			
			performOperation(choice);
			
		} while(true);
	}

	private void performOperation(int choice) throws ClassNotFoundException, SQLException {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			try {
				updateAccount();
			} catch (AccountNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			break;
			
		case 3:
			closeAccount();
			break;
		case 4:
				searchAccount();
			break;
		
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			try {
				checkCurrentBalance();
			} catch (AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortAccounts();
			break;
			
		case 11:
			
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}
	private  void sortAccounts()  {

		System.out.println("Enter 1 for sort According to name...");
	
		
		System.out.println("Enter 2 for sort According to balance...");
		
		
		System.out.println("Enter 3 for sort According to accountNumber...");
		
		
		System.out.println("Enter your choice..");
		int choice = scanner.nextInt();
		switch(choice){
		case 1:
		try {
			List<SavingsAccount> sotredOrderist = new ArrayList<SavingsAccount>(); 
			sotredOrderist=savingsAccountService.sortByAccountHolderName();
			for (SavingsAccount savingsAccount : sotredOrderist) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
		
		case 2:
			
				List<SavingsAccount> sotredOrderist = new ArrayList<SavingsAccount>(); 
			try {
				sotredOrderist=savingsAccountService.sortByAccountBalance();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				for (SavingsAccount savingsAccount : sotredOrderist) {
					System.out.println(savingsAccount);
				}
			
			break;
			
		case 3:
			 sotredOrderist = new ArrayList<SavingsAccount>(); 
			try {
				sotredOrderist=savingsAccountService.sortByAccountNumber();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (SavingsAccount savingsAccount : sotredOrderist) {
				System.out.println(savingsAccount);
			}
		break;
	}
	}

	private void updateAccount() throws AccountNotFoundException {
		System.out.println("1.to update name...");
		System.out.println("2.to update Salary Type...");
		System.out.println("Eneter your choice");
		int choice = scanner.nextInt();
		System.out.println("Enter account_id to update....");
		int accountNumber = scanner.nextInt();
		boolean salary = false;
		String newAccountHolderName="";
		switch (choice) {
		
		case 1:	
			System.out.println("Enter new name to update....");
			newAccountHolderName = scanner.next();
			
			SavingsAccount sn = null;
			try {
				sn = savingsAccountService.updateAccount(newAccountHolderName,accountNumber,salary);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			break;
			
		case 2:
			System.out.println("change salary type");
			salary = scanner.nextBoolean();
			
			SavingsAccount sn1 = null;
			try {
				sn1 = savingsAccountService.updateAccount(newAccountHolderName,accountNumber,salary);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			break;
		default:
			break;
		}

		
	}

	private void searchAccount() {
		System.out.println("1.Enter account number to get details...");
		System.out.println("2.Enter accountHoledrName to get details...");
	
		System.out.println("Enter Your Choice");
		int choice = scanner.nextInt();
		switch(choice){
		
		case 1:
			System.out.println("Enter accountNumber...");
			int accountNumber = scanner.nextInt();
			SavingsAccount sa = null;
			try {
				sa = savingsAccountService.seachByAccountNumber(accountNumber);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sa);
			break;
			
		case 2:
			System.out.println("Enter account holder name...");
			String accountHolderName = scanner.next();
			SavingsAccount sad = null;
			try {
				sad = savingsAccountService.AccountHolderName(accountHolderName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.println(sad);
			break;
		}
	}

	private void checkCurrentBalance() throws AccountNotFoundException {
		System.out.println("Enter Account NUmber:");
		int accountNumber = scanner.nextInt();
		try {
			double accountBalance = savingsAccountService.checkBalance(accountNumber);
			System.out.println(accountBalance);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeAccount()  {
		System.out.println("Enter account NUmber to close account");
		int accountToCloseAccount = scanner.nextInt();
		try {
			savingsAccountService.deleteAccount(accountToCloseAccount);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			
		}
	}

	private void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
		}
	}

	private void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");
			
			int choice = scanner.nextInt();
			
		}while(true);
		
	}

	private void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void acceptInput(String type) {
		if(type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance=0.0;
			if(!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n")?false:true;
			createSavingsAccount(accountHolderName,accountBalance, salary);
		}
	}

	private void createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}



