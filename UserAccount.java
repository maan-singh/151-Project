package hotelsystem;

import java.util.ArrayList;

/**
 * @author ParmeetSingh
 * @version	1.2
 *
 *	Represents a user account in the hotel system that can be either a guest or a manager
 */
public class UserAccount 
{
	private enum UserAccountType
	{
		Manager,
		Guest
	}
	
	private String username;
	private String password;
	private int id;
	private UserAccountType userAccountType;
	
	//private ArrayList<Reservation> reservations;
	
	public UserAccount(String name, String password, int id, UserAccountType userAccountType) 
	{
		this.username = name;
		this.password = password;
		this.id = id;
		this.userAccountType = userAccountType;
	}

	public String getName()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getID()
	{
		return id;
	}
	
	public UserAccountType getUserAccountType()
	{
		return this.userAccountType;
	}
}
