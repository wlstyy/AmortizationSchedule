package exercises.util;

public class StringFormatHelper {
	//fomat for the header
	private static String headerFormatString = "%1$-20s%2$-20s%3$-20s%4$-20s%5$-20s%6$-20s\n";
	// format for ResultlineItem output is in dollars
	private static String lineItemFormatString = "%1$-20d%2$-20.2f%3$-20.2f%4$-20.2f%5$-20.2f%6$-20.2f\n";


	/**
	 * @return the headerFormat
	 */
	
	public static String getHeaderFormatString() {
		return headerFormatString;
	}
	/**
	 * @param headerFormat the headerFormat to set
	 */
	
	public static void setHeaderFormatString(String headerFormatString) {
		StringFormatHelper.headerFormatString = headerFormatString;
	}
	/**
	 * @return the lineItemFormat
	 */
	
	public static String getLineItemFormatString() {
		return lineItemFormatString;
	}
	/**
	 * @param lineItemFormat the lineItemFormat to set
	 */
	
	public static void setLineItemFormatString(String lineItemFormatString) {
		StringFormatHelper.lineItemFormatString = lineItemFormatString;
	}
}
