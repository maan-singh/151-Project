package project;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a reservation for room
 * 
 * @author TeamVoid
 * @version 1.2
 */
public class Reservation
//implements java.io.Serializable
{
	private Date _checkInDate; 
	private Date _checkOutDate;
	private Room _room;
	private UserAccount _reservationAccount;
	
	private SimpleDateFormat formattor;
	
	/**
	 * Constructor for Reservation
	 * @param checkInDate of reservation
	 * @param checkOutDateof reservation
	 * @param room
	 * @throws Exception
	 */
	public Reservation(Date checkInDate, Date checkOutDate, Room room) throws Exception 
	{
		this._room = room;
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	
	
	/**
	 * COnstructor for Reservation WITH a user account associated to it
	 * @param checkInDateof reservation
	 * @param checkOutDateof reservation
	 * @param room
	 * @param reservationAccount of reservation
	 * @throws Exception
	 */
	public Reservation(Date checkInDate, Date checkOutDate, Room room, UserAccount reservationAccount) throws Exception 
	{
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
		this._room = room;
		this.set_reservationAccount(reservationAccount);
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	/**
	 * Gets the check in date for the reservation
	 * @return _checkInDate of reservation
	 */
	public Date getCheckInDate() 
	{ 
		return _checkInDate; 
	}
	
	/**
	 * Gets the check out date for the reservation
	 * @return _checkOutDate of reservation
	 */
	public Date getCheckOutDate() 
	{ 
		return _checkOutDate; 
	}
	
	/**
	 * Gets the room for the reservation
	 * @return _room
	 */
	public Room getRoom()
	{
		return _room;
	}
	
	/**
	 * Gets the reservation account for the reservation
	 * @return _reservationAccount
	 */
	public UserAccount get_reservationAccount() 
	{
		return _reservationAccount;
	}

	/**
	 * Sets the reservation account for the reservation
	 */
	public void set_reservationAccount(UserAccount _reservationAccount) 
	{
		this._reservationAccount = _reservationAccount;
	}
	

	/**
	 * Checks if there's any conflict between the reservations selected by guest
	 * @param otherCheckIn of reservation
	 * @param otherCheckOut of reservation
	 * @return true/false if there's a conflict or not
	 */
	public boolean checkConflict(Date otherCheckIn, Date otherCheckOut) 
	{
		if((_checkInDate.compareTo(otherCheckOut) < 0 && _checkOutDate.compareTo(otherCheckOut) > 0) || (_checkOutDate.compareTo(otherCheckIn) > 0  && _checkInDate.compareTo(otherCheckIn) < 0)) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public String toString() 
	{
		return " Check In Date: " + formattor.format(_checkInDate) + "\n" + 
				   " Check Out Date: " + formattor.format(_checkOutDate) + "\n" +
				   " Room Number: " + getRoom();	}

	
}
