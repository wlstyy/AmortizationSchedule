// Exercise Details:
// Build an amortization schedule program using Java. 
// 
// The program should prompt the user for
//		the amount he or she is borrowing,
//		the annual percentage rate used to repay the loan,
//		the term, in years, over which the loan is repaid.  
// 
// The output should include:
//		The first column identifies the payment number.
//		The second column contains the amount of the payment.
//		The third column shows the amount paid to interest.
//		The fourth column has the current balance.  The total payment amount and the interest paid fields.
// 
// Use appropriate variable names and comments.  You choose how to display the output (i.e. Web, console).  
// Amortization Formula
// This will get you your monthly payment.  Will need to update to Java.
// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));  
// 
// Where:
// P = Principal
// I = Interest
// J = Monthly Interest in decimal form:  I / (12 * 100) 
// N = Number of months of loan
// M = Monthly Payment Amount
// 
// To create the amortization table, create a loop in your program and follow these steps:
// 1.      Calculate H = P x J, this is your current monthly interest
// 2.      Calculate C = M - H, this is your monthly payment minus your monthly interest, so it is the amount of principal you pay for that month
// 3.      Calculate Q = P - C, this is the new balance of your principal of your loan.
// 4.      Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
// 
package exercises;

import exercises.util.*;
import exercises.items.*;

import java.util.List;
import java.util.ArrayList;

public class AmortizationSchedule_II {
	private long amountBorrowed = 0;		// in cents
	private double apr = 0d;
	private int initialTermMonths = 0;

	private final double monthlyInterestDivisor = 12d * 100d;
	private double monthlyInterest = 0d;
	private double monthlyPaymentAmount = 0;//change to double in this version
	//old versoin
	//private long monthlyPaymentAmount = 0;
	private List<ResultLineItem> scheduleTable;
	
	/**
	 * Calculate the Monthly Payment
	 * @return
	 */
	public AmortizationSchedule_II(InputItem input) throws IllegalArgumentException {
		if (!isValid(input)) {
			throw new IllegalArgumentException();  //check twice?
		}
		amountBorrowed = Math.round(input.getAmount() * 100);
		apr = input.getApr();
		initialTermMonths = input.getYears() * 12;
		monthlyPaymentAmount = calculateMonthlyPayment();
		if (monthlyPaymentAmount > amountBorrowed) {
			throw new IllegalArgumentException();
		}
		scheduleTable=calculateScheduleTable();
	}
	
	private boolean isValid(InputItem input){
		return AmortizationScheduleUtil.isValidAPRValue(input.getApr())
				&& AmortizationScheduleUtil.isValidBorrowAmount(input.getAmount())
				&& AmortizationScheduleUtil.isValidTerm(input.getYears());
	}
	
	private double calculateMonthlyPayment() {
		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		//
		// Where:
		// P = Principal
		// I = Interest
		// J = Monthly Interest in decimal form:  I / (12 * 100)
		// N = Number of months of loan
		// M = Monthly Payment Amount
		// 

		// calculate J
		monthlyInterest = apr / monthlyInterestDivisor;

		// this is 1 / (1 + J)
		double tmp = Math.pow(1d + monthlyInterest, -1);

		// this is Math.pow(1/(1 + J), N)
		tmp = Math.pow(tmp, initialTermMonths);

		// this is 1 / (1 - (Math.pow(1/(1 + J), N))))
		tmp = Math.pow(1d - tmp, -1);

		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		double rc = amountBorrowed * monthlyInterest * tmp;

		return rc;
	}
	
	/**
	 * Calculate the AmortizationSchedule Table
	 * The old version may require 1 more month payment for rounding error which I do not like
	 * The newest version will not do so. But the monthly payment may change +-1 cent for different months
	 * @param args
	 */
	private List<ResultLineItem> calculateScheduleTable(){
		List<ResultLineItem> list=new ArrayList<ResultLineItem>();
		long balance = amountBorrowed;
		int paymentNumber = 0;
		long totalPayments = 0;
		long totalInterestPaid = 0;
		list.add(new ResultLineItem(paymentNumber++,0l,0l,amountBorrowed,totalPayments,totalInterestPaid));
		final int maxNumberOfPayments = initialTermMonths;//Not initialTermMonths here.
		//old version
		//final int maxNumberOfPayments = initialTermMonths+1;
		while ((balance > 0) && (paymentNumber <= maxNumberOfPayments)){
			// Calculate H = P x J, this is your current monthly interest in double
			double curMonthlyInterest_d=balance*monthlyInterest;
			// the amount required to payoff the loan in double
			double curPayoffAmount_d = balance + curMonthlyInterest_d;
			// the amount to payoff the remaining balance may be less than the calculated monthlyPaymentAmount in double
			double curMonthlyPaymentAmount_d = Math.min(monthlyPaymentAmount, curPayoffAmount_d);
			// it's possible that the calculated monthlyPaymentAmount is 0,
						// or the monthly payment only covers the interest payment - i.e. no principal
						// so the last payment needs to payoff the loan
			if ((paymentNumber == maxNumberOfPayments) &&
					((curMonthlyPaymentAmount_d == 0) || (curMonthlyPaymentAmount_d == curMonthlyInterest_d))) {
				curMonthlyPaymentAmount_d = curPayoffAmount_d;
			}
			// Calculate C = M - H, this is your monthly payment minus your monthly interest,
			// so it is the amount of principal you pay for that month in double
			double curMonthlyPrincipalPaid_d = curMonthlyPaymentAmount_d - curMonthlyInterest_d;
			//convert to long
			long curMonthlyInterest = Math.round(curMonthlyInterest_d);
			//convert to long
			long curMonthlyPrincipalPaid = Math.round(curMonthlyPrincipalPaid_d);
			//this time the monthly payment is sum(round()) rather than round(sum())
			long curMonthlyPaymentAmount=curMonthlyInterest+curMonthlyPrincipalPaid;
			// Calculate Q = P - C, this is the new balance of your principal of your loan.
			long curBalance = balance - curMonthlyPrincipalPaid;
		
			 //old version
			 /**
			// Calculate H = P x J, this is your current monthly interest
			long curMonthlyInterest = Math.round(((double) balance) * monthlyInterest);

			// the amount required to payoff the loan
			long curPayoffAmount = balance + curMonthlyInterest;
			
			// the amount to payoff the remaining balance may be less than the calculated monthlyPaymentAmount
			long curMonthlyPaymentAmount = Math.min(monthlyPaymentAmount, curPayoffAmount);
			
			// it's possible that the calculated monthlyPaymentAmount is 0,
			// or the monthly payment only covers the interest payment - i.e. no principal
			// so the last payment needs to payoff the loan
			if ((paymentNumber == maxNumberOfPayments) &&
					((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
				curMonthlyPaymentAmount = curPayoffAmount;
			}
			
			// Calculate C = M - H, this is your monthly payment minus your monthly interest,
			// so it is the amount of principal you pay for that month
			long curMonthlyPrincipalPaid = curMonthlyPaymentAmount - curMonthlyInterest;
			
			// Calculate Q = P - C, this is the new balance of your principal of your loan.
			long curBalance = balance - curMonthlyPrincipalPaid;
			 */
			totalPayments += curMonthlyPaymentAmount;
			totalInterestPaid += curMonthlyInterest;
			//add this record in result list
			list.add(new ResultLineItem(paymentNumber++
					,curMonthlyPaymentAmount
					,curMonthlyInterest
					,curBalance,totalPayments
					,totalInterestPaid));
			// Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
			balance = curBalance;
		}
		return list; 
	}
	
	/**
	 * Output the Amortization Schedule Table
	 */
	public void outputAmortizationSchedule(){
		//print the title of the table
		AmortizationScheduleUtil.printHeader();
		//print schedule table
		for(ResultLineItem line:scheduleTable){
			ConsoleInputOutput.print(line.toString());;
		}
	}
	
	public static void main(String[] args) {
		//read input from the console
		InputItem input=AmortizationScheduleUtil.readUserInput();
		try{
			AmortizationSchedule_II as = new AmortizationSchedule_II(input);
			as.outputAmortizationSchedule();
		}catch (IllegalArgumentException e) {
			ConsoleInputOutput.print("Unable to process the values entered. Terminating program.\n");
		}
	}

}
