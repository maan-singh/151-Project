import java.util.ArrayList;

/**
 * SimpleReceipt
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
				"\nReserved Rooms:\n" + userAccount.getRoomsReserved() + 
				"\nTotal Balance Due: $" + userAccount.getTotalBalance();
	
	}
}
