package hotelsystem;

import java.util.ArrayList;

/**
 * @author ParmeetSingh
 * @version	1.2
 *
 *	Represents a user account in the hotel system that can be either a guest or a manager
 */
public class User 
{
	private String username;
	private String password;
	private int id;
	
	//private ArrayList<Reservation> reservations;
	
	public User(String name, String password, int id) 
	{
		this.username = name;
		this.password = password;
		this.id = id;
		
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
}
