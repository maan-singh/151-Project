/**
 * Class for a comprehensive receipt provided by the hotel staff.
 * @author TeamVoid
 * Receipt that shows more information than simple receipt.
 */
public class ComprehensiveReceipt implements Receipt 
{
	/**
	 * Method that prints the details of a receipt with the total balance in a Comprehensive Format.
	 * Comprehensive Format - shows all valid reservations made by a user with the corresponding total amount due.
	 * @param UserAccount a user account
	 * @return a string of details of the receipt in a Comprehensive Format.
	 */
	public String printReceipt(UserAccount userAccount)
	{
		String compList = userAccount.comprehensiveReceiptList(); // string that has all the reservations made by a user account
		
		return "Comprehensive Receipt: " + 
				"\n\nUser: " + userAccount.getName() + 
				"\nUserID: " + userAccount.getID() + 
				"\nReservations:\n" + compList + 
				"\nTotal Balance Due: $" + userAccount.getBalance();
	}
}
