/**
 * Interface for a receipt.
 * @author TeamVoid
 * Interface to be used to show receipt, either Comprehensive or Simple
 */
public interface Receipt 
{
	/**
	 * Method to print the receipt that shows the balance to be paid by a user. (To be over ridden)
	 * @param userAccount a user account
	 * @return String a string that has the balance to be paid
	 */
	public String printReceipt(UserAccount userAccount);	
}