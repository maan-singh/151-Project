package project;

import java.util.ArrayList;

/**
 * Class for an user account that represents a user account in the hotel reservation system that can either be a guest or a manager.
 * @author TeamVoid
 * 
 */
public class UserAccount 
{
	/**
	 * Enum class to store 'Manager' and 'Guest' types to differentiate between kind of user accounts.
	 * @author TeamVoid
	 *
	 */
	private enum UserAccountType
	{
		Manager,
		Guest
	}

	private String username; // user name of the user account
	private String password; // password of the user account
	private int id; // id number of the user account
	private static int genID = 0; 
	private UserAccountType userAccountType; // enum for specifying type of user (can be guest or manager)	
	private ArrayList <Reservation> list; // array list to store a list of reservations made by a user

	/**
	 * Constructor for UserAccount class.
	 * @param name of the user account
	 * @param password of the user account
	 * @param id of the user account
	 * @param userAccountType (guest or manager)
	 */
	public UserAccount(String name, String password, UserAccountType userAccountType) 
	{
		this.username = name;
		this.password = password;
		this.id = genID++;
		//this.id = id;
		this.userAccountType = userAccountType;	
		list = new ArrayList<Reservation>();
	}

	/**
	 * Returns whether the user is guest or manager.
	 * @return user type (guest or manager)
	 */
	public UserAccountType getUserAccountType()
	{
		return this.userAccountType;
	}

	/**
	 * Gets the name of the user account.
	 * @return user name of user account
	 */
	public String getName()
	{
		return username;
	}

	/**
	 * Gets the password for the user account.
	 * @return password of the user account
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets a new password
	 * @param pw
	 */
	public void setPassword(String pw)
	{
		password = pw;
	}
	/**
	 * Gets the ID number of the user account.
	 * @return id number of the user account
	 */
	public int getID()
	{
		return id;
	}

	/**
	 * Adds a reservation to the array list of reservations.
	 * @param res new reservation to be added
	 */
	public void addReservation(Reservation res)
	{
		list.add(res);
	}

	/**
	 * Removes a reservation from the array list of reservations.
	 * @param res existing reservation to be removed
	 */
	public void removeReservation(Reservation res)
	{
		list.remove(res);
	}

	/**
	 * Returns all reservations made by an user account.
	 * @return reservations a string of all reservations in the array list
	 */
	public String getReservations()
	{
		String reservations= "";
		int sizeOfReservations= list.size();
		for(int a=0; a<sizeOfReservations; a++) {
			reservations = reservations + list.get(a).getRoom().getNiceRoomNumber() + "\n";
		}
		return "Rooms Reserved: " + reservations;
	}
	
	/**
	 * Prints a comprehensive receipt for the User Account
	 * @return
	 */
	public String comprehensiveReceiptList() {
		ArrayList<Reservation> resList = list;
		String list = "";
		for (Reservation a: resList) {
			list = list + a + "\n";
		}
		return list;
	}

	/**
	 * Returns the balance to be paid on the user account made by the reservations.
	 * @return balance on a receipt
	 */
	public int getBalance()
	{
		int balance = 0;

		/*
		 * Loop to iterate through the array list of reservations of made by the user and fetches the price associated with each reservation.
		 */
		for (Reservation a:list) {
			if (a.getRoom().getRoomType() == Room.RoomType.Luxurious) {
				balance = balance + 300;
			}
			else if (a.getRoom().getRoomType() == Room.RoomType.Economic) {
				balance = balance + 100;
			}
		}
		return balance;
	}

	/**
	 * Method that returns a string of the user name of the account.
	 */
	public String toString()
	{
		return "Name: " + getName(); 
	}
}
