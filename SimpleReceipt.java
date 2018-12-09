
/**
 * SimpleReceipt Class
 * @author TeamVoid
 * Class that shows a simple list of reservations for a given user and the total 
 */
public class SimpleReceipt implements Receipt 
{
	public String printReceipt(UserAccount userAccount)
	{	
		return "Simple Receipt:" + 
				"\nUser: " + userAccount.getName() + 
				"\nUserID: " + userAccount.getID() + 
				"\nReserved Rooms:\n" + userAccount.getReservations() + 
				"\nTotal Balance Due: $" + userAccount.getBalance();
	
	}
}
