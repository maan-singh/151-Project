
/**
 * ComprehensiveReceipt Class
 * @author TeamVoid
 * Receipt that shows more information than a SimpleReceipt
 */

public class ComprehensiveReceipt implements Receipt 
{
	/**
	 * printReceipt()
	 * @param UserAccount userAccount
	 * @return String
	 * Method to show receipt details in a comprehensive manner
	 */
	public String printReceipt(UserAccount userAccount)
	{
		//Grab all the reservations
		String reservationList = userAccount.getReservations();
		
		return "Comprehensive Receipt: " + 
				"\n\nUser: " + userAccount.getName() + 
				"\nUserID: " + userAccount.getID() + 
				"\nReservations:\n" + reservationList + 
				"\nTotal Balance Due: $" + userAccount.getBalance();
	}
}
