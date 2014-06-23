/**
 * class for Each Line of the result table
 */

package exercises.items;

import exercises.util.StringFormatHelper;

public class ResultLineItem {
	private int paymentNumber;
	private long curMonthlyPaymentAmount;
	private long curMonthlyInterest;
	private long curBalance;
	private long totalPayments;
	private long totalInterestPaid;
	public ResultLineItem(int paymentNumber, long curMonthlyPaymentAmount, long curMonthlyInterest, long curBalance, long totalPayments, long totalInterestPaid){
		this.paymentNumber=paymentNumber;
		this.curMonthlyPaymentAmount=curMonthlyPaymentAmount;
		this.curMonthlyInterest=curMonthlyInterest;
		this.curBalance=curBalance;
		this.totalPayments=totalPayments;
		this.totalInterestPaid=totalInterestPaid;
	}
	public long getCurMonthlyPaymentAmount(){
		return this.curMonthlyPaymentAmount;
	}
	public long getCurMonthlyInterest(){
		return this.curMonthlyInterest;
	}
	public long getCurBalance(){
		return this.curBalance;
	}
	public long getTotalPayments(){
		return this.totalPayments;
	}
	public long getTotalInterestPaid(){
		return this.totalInterestPaid;
	}
	/**
	 * toString() function
	 */
	public String toString(){
		String format=StringFormatHelper.getLineItemFormatString();
		return String.format(format, paymentNumber,((double) curMonthlyPaymentAmount) / 100d,
				((double) curMonthlyInterest) / 100d,
				((double) curBalance) / 100d,
				((double) totalPayments) / 100d,
				((double) totalInterestPaid) / 100d);
	}
}
