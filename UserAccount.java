package project;

import java.util.ArrayList;

/**
 * @author TeamVoid
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
	private static int genID = 0;
	private UserAccountType userAccountType;
	
	private ArrayList <Reservation> list;
	
	
	/**
	 * Constructor for UserAccount
	 * @param name 0f the user account
	 * @param password of the user account
	 * @param id of the user account
	 * @param userAccountType
	 */
	public UserAccount(String name, String password, int id, UserAccountType userAccountType) 
	{
		this.username = name;
		this.password = password;
		this.id = genID++;
		this.userAccountType = userAccountType;
		
		list = new ArrayList<Reservation>();
		
	}

	
	/**
	 * Returns whether the user is guest or manager
	 * @return user type
	 */
	public UserAccountType getUserAccountType()
	{
		return this.userAccountType;
	}

	
	/**
	 * Gets the name of the user
	 * @return username
	 */
	public String getName()
	{
		return username;
	}
	
	/**
	 * Gets the password for the user account
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Gets the ID of the user account
	 * @return
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Adds reservation to the list
	 * 
	 * @param res
	 */
	public void addReservation(Reservation res)
	{
		list.add(res);
	}
	
	/**
	 * Removes reservation from the list
	 * @param res 
	 */
	public void removeReservation(Reservation res)
	{
		list.remove(res);
	}
	
	/**
	 * Returns all reservations by the account
	 * @return reservations a string of all reservations
	 */
	public String getReservations()
	{
		String reservations = "";
		for(int r = 0; r < list.size(); r++)
			reservations = reservations + list.get(r).getRoom().getNiceRoomNumber() + "\n";

		return "Rooms Reserved: " + reservations;
	}
	
	/**
	 * Gets the balance on the User Account
	 * @return balance on receipt
	 */
	public int getBalance()
	{
		int balance = 0;
		
		for (Reservation r : list)
		{
			if (r.getRoom().getRoomType() == Room.RoomType.Economic)
				balance = balance + 100;
			
			else if (r.getRoom().getRoomType() == Room.RoomType.Luxurious)
				balance = balance + 300;
		}
		return balance;
	}
	
	public String toString()
	{
		return "Name: " + getName(); 
	}
	
}
