/**
 * Bank is the primary class for the banking application, housing all methods to process user input
 * The actual account computations occur in the account classes Checking, Saving, and Loan
 */
package edu.gcu.bootcamp.cst135.milestone.controller;

import java.util.Scanner;

public class Bank {

	//Class data
	Scanner scanner = new Scanner(System.in);
	Saving saving = new Saving("-SAV12345",5000,200,25,.06);
	Checking checking = new Checking("-CHK23456",5000,25);

	/**
	 * Outputs a message to the customer when exiting the banking app
	 */
	private void viewExitScreen() {
		System.out.println("Goodbye, Have a good day!");
	}

	/**
	 * Displays the main menu and gets a user selection.
	 * If the user enters a non-integer, parseInt throws NumberFormatException
	 * which gets caught and calls viewCustomerMenu again
	 */
	public void viewCustomerMenu() {

		try {
			int option;
			do {
				System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("                MAIN MENU");
				System.out.println("                GCU BANK");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("Pick an option: ");
				System.out.println("-----------------------");
				System.out.println(" 1: Deposit to Checking");
				System.out.println(" 2: Deposit to Savings");
				System.out.println(" 3: Withdraw from Checking");
				System.out.println(" 4: Withdraw from Savings");			
				System.out.println(" 5: Get balance");
				System.out.println(" 6: Get monthly statement");
				System.out.println("------------------------");
				System.out.println(" 9: : Logout");
				//try to convert user input into an integer (throws InputMismatchException if not)
				option = scanner.nextInt();
				processCustomerMenu(option);
			} while (option != 9);
		}catch(Exception e) {  //generated by nextInt()
			System.out.println("Wrong input: Enter the numbers 1-6 or 9 to Logout\n");
			//When a scanner throws an InputMismatchException, the scanner will not pass the token
			//that caused the exception, so that it may be retrieved or skipped via some other method.
			//So, read the token that caused the exception so it's not in the scanner anymore
			scanner.nextLine();
			//Re-call the menu method
			viewCustomerMenu();
		}
	}

	/**
	 * Calls a method to display the screen to process the user-selected option from the main menu
	 * After each transaction, calls viewBalances to update the user
	 * @param parseInt
	 */
	private void processCustomerMenu(int parseInt) {

		switch(parseInt) {
		case 1: viewDepositChecking();viewBalances();
		break;
		case 2: viewDepositSavings();viewBalances();
		break;
		case 3: viewWithdrawalChecking();viewBalances();
		break;
		case 4: viewWithdrawalSavings();viewBalances();
		break;
		case 5: viewBalances();
		break;
		case 6: viewEndOfMonth();viewBalances();
		break;  
		case 9: viewExitScreen();
		break;
		default: System.out.println("Invalid menu selection:"); viewCustomerMenu();
		}
	}
	
	/**
	 * Shows the end of month screen and performs the end-of-month calculations
	 */
	private void viewEndOfMonth() {

		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("               END OF MONTH");
		System.out.println("                 GCU BANK");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

		//Determine if a service fee is required
		if(saving.getAccountBalance() < saving.getMinBalance()) {
			System.out.printf("A $%.2f service fee is being assessed for below minimum balance in savings", saving.getServiceFee());
			saving.setAccountBalance(saving.getAccountBalance() - saving.getServiceFee());
		}
		//Compute interest on any positive balance
		if (saving.getAccountBalance() > 0){
			saving.setAccountBalance(saving.getAccountBalance() + (saving.getInterest() * saving.getAccountBalance()));
		}	
	}		

	/**
	 * Displays the screen for a withdrawal from checking
	 * nextDouble will throw an exception which gets caught and just re-calls the view method
	 */
	private void viewWithdrawalChecking() {

		try {
			System.out.println("How much would you like to withdraw: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			//If the user entered a valid number, process the withdrawal request
			processWithdrawalChecking(input);
		}catch(Exception e) {
			System.out.println("Wrong input: Enter a positive number such as 123.45\n");
			viewWithdrawalChecking();
		}
	}

	/**
	 * Performs a withdrawal from a checking account
	 * @param input
	 */
	private void processWithdrawalChecking(double input) {

		//Determine if the account will be overdrawn; if so, alert the user
		if(checking.getAccountBalance() < input) {
			System.out.println("A $" + checking.getOverdraft() + " overdraft fee will be assessed if you continue. Continue Y or N?");
			//If the user chooses to continue, assess the overdraft fee; if not, return to the checking withdrawal screen
			if(scanner.nextLine().toLowerCase().equals("y")) {
				checking.setAccountBalance(checking.getAccountBalance() - input - checking.getOverdraft());
			}else
				viewWithdrawalChecking();
		}else
			checking.setAccountBalance(checking.getAccountBalance() - input);
	}

	/**
	 * Displays the screen for a deposit into savings
	 * nextDouble will throw an exception which gets caught and just re-calls the view method
	 */
	private void viewDepositSavings() {

		try {
			System.out.println("How much would you like to deposit: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processDepositSavings(input);
		}catch(Exception e) {
			System.out.println("Wrong input: Enter a positive number such as 123.45\n");
			viewDepositSavings();
		}
	}

	/**
	 * Performs a deposit into a savings account
	 * @param input
	 */
	private void processDepositSavings(double input) {

		saving.setAccountBalance(saving.getAccountBalance() + input);
	}

	/**
	 * Displays the screen for a deposit into checking
	 * nextDouble will throw an exception which gets caught and just re-calls the view method
	 */
	private void viewDepositChecking() {

		try {
			System.out.println("How much would you like to deposit: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processDepositChecking(input);
		}catch(Exception e) {
			System.out.println("Wrong input: Enter a positive number such as 123.45\n");
			viewDepositChecking();
		}
	}

	/**
	 * Performs a deposit into a checking account
	 * @param input
	 */
	private void processDepositChecking(double input) {

		checking.setAccountBalance(checking.getAccountBalance() + input);
	}

	/**
	 * Displays the screen for a withdrawal from savings
	 * nextDouble will throw an exception which gets caught and just re-calls the view method
	 */
	private void viewWithdrawalSavings() {

		try {
			System.out.println("How much would you like to withdraw: ");
			double input = scanner.nextDouble();
			scanner.nextLine();
			processWithdrawalSavings(input);
		}catch(Exception e) {
			System.out.println("Wrong input\n");
			viewWithdrawalSavings();
		}
	}

	/**
	 * Performs a withdrawal from a savings account
	 * @param input
	 */
	private void processWithdrawalSavings(double input) {
		//NEEDS TO CHECK THAT THE AVAILABLE BALANCE IS LARGE ENOUGH
		saving.setAccountBalance(saving.getAccountBalance() - input);	
	}

	/**
	 * Displays all account balances
	 */
	private void viewBalances() {

		System.out.println("\n------------------------");	
		System.out.println(saving.toString());
		System.out.println(checking.toString());
		System.out.println("------------------------");
	}
}