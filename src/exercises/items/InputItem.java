/**
 * Wrapped Input for Amortization Schedule
 */

package exercises.items;

public class InputItem {
	
	private double amount = 0;
	private double apr = 0;
	private int years = 0;

	public InputItem(double amount, double apr, int years){
		this.amount=amount;
		this.apr=apr;
		this.years=years;
	}
	public double getAmount() {
		return amount;
	}
	public double getApr() {
		return apr;
	}

	public int getYears() {
		return years;
	}
	
}
