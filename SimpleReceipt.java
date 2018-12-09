/**
 * Class SimpleReceipt
 * @author TeamVoid
 * Class that shows a simple list of reservations for a given user and the total 
 */
public class SimpleReceipt implements Receipt 
{
	/**
	 * Method that returns a string of the receipt details for a specific user in a simple format.
	 * Simple Format - shows the reservations and the total due made by the current transaction with the corresponding total amount due.
	 * @param userAccount a user account
	 * @return a string with the details of the receipt in a Simple Format.
	 */
	public String printReceipt(UserAccount userAccount)
	{	
		return "Simple Receipt:" + 
				"\nUser: " + userAccount.getName() + 
				"\nUserID: " + userAccount.getID() + 
				"\nReserved Rooms:\n" + userAccount.getReservations() + 
				"\nTotal Balance Due: $" + userAccount.getBalance();
	}
}
