/**
 * Class to check the input from the console;
 */
package exercises.util;

import exercises.items.InputItem;

import java.lang.NumberFormatException;
import java.io.IOException;

public class AmortizationScheduleUtil {
	private static final double[] borrowAmountRange = new double[] { 0.01d, 1000000000000d };
	private static final double[] aprRange = new double[] { 0.000001d, 100d };
	private static final int[] termRange = new int[] { 1, 1000000 };

	public static final double[] getBorrowAmountRange() {
		return borrowAmountRange;
	}

	public static final double[] getAPRRange() {
		return aprRange;
	}

	public static final int[] getTermRange() {
		return termRange;
	}
/**
 * Check amountBorrowed
 * @param amount
 * @return
 */
	public static boolean isValidBorrowAmount(double amount) {
		double range[] = getBorrowAmountRange();
		return ((range[0] <= amount) && (amount <= range[1]));
	}
/**
 * check year rate
 * @param rate
 * @return
 */
	public static boolean isValidAPRValue(double rate) {
		double range[] = getAPRRange();
		return ((range[0] <= rate) && (rate <= range[1]));
	}
/**
 * check years
 * @param years
 * @return
 */
	public static boolean isValidTerm(int years) {
		int range[] = getTermRange();
		return ((range[0] <= years) && (years <= range[1]));
	}
/**
 * print some title
 */
	public static void printHeader(){
		String formatString = StringFormatHelper.getHeaderFormatString();
		ConsoleInputOutput.printf(formatString,
				"PaymentNumber", "PaymentAmount", "PaymentInterest",
				"CurrentBalance", "TotalPayments", "TotalInterestPaid");
	}
/**
 * 
 * @return
 */
	public static InputItem readUserInput(){
		String[] userPrompts = {
				"Please enter the amount you would like to borrow: ",
				"Please enter the annual percentage rate used to repay the loan: ",
				"Please enter the term, in years, over which the loan is repaid: " };

		String line = "";
		double amount = 0;
		double apr = 0;
		int years = 0;

		for (int i = 0; i < userPrompts.length;) {
			String userPrompt = userPrompts[i];
			try {
				line = ConsoleInputOutput.readLine(userPrompt);
			} catch (IOException e) {
				ConsoleInputOutput
						.print("An IOException was encountered. Terminating program.\n");
				//return input;
			}

			boolean isValidValue = true;
			try {
				switch (i) {
				case 0:
					amount = Double.parseDouble(line);
					if (isValidBorrowAmount(amount) == false) {
						isValidValue = false;
						double range[] = getBorrowAmountRange();
						ConsoleInputOutput
								.print("Please enter a positive value between "
										+ range[0] + " and " + range[1] + ". ");
					}
					break;
				case 1:
					apr = Double.parseDouble(line);
					if (isValidAPRValue(apr) == false) {
						isValidValue = false;
						double range[] = getAPRRange();
						ConsoleInputOutput
								.print("Please enter a positive value between "
										+ range[0] + " and " + range[1] + ". ");
					}
					break;
				case 2:
					years = Integer.parseInt(line);
					if (isValidTerm(years) == false) {
						isValidValue = false;
						int range[] = getTermRange();
						ConsoleInputOutput
								.print("Please enter a positive integer value between "
										+ range[0] + " and " + range[1] + ". ");
					}
					break;
				}
			} catch (NumberFormatException e) {
				isValidValue = false;
			}
			if (isValidValue) {
				i++;
			} else {
				ConsoleInputOutput.print("An invalid value was entered.\n");
			}
		}
		return new InputItem(amount,apr,years);
	}
	
}
