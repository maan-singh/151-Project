/**
 * Receipt interface
 * @author TeamVoid
 * Interface to be used to show receipt, either Comprehensive or Simple
 */
public interface Receipt 
{
	/**
	 * printReceipt()
	 * @param userAccount
	 * @return String
	 * Method to show receipt details of specific user account
	 */
	public String printReceipt(UserAccount userAccount);
	
}
