package project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation
//implements java.io.Serializable
{
	private Date _checkInDate; 
	private Date _checkOutDate;
	private Room _room;
	private UserAccount _reservationAccount;
	
	private SimpleDateFormat formattor;
	
	public Reservation(Date checkInDate, Date checkOutDate, Room room) throws Exception 
	{
		this._room = room;
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	
	
	public Reservation(Date checkInDate, Date checkOutDate, Room room, UserAccount reservationAccount) throws Exception 
	{
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
		this._room = room;
		this.set_reservationAccount(reservationAccount);
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	public Date getCheckInDate() 
	{ 
		return _checkInDate; 
	}
	
	public Date getCheckOutDate() 
	{ 
		return _checkOutDate; 
	}
	
	public Room getRoom()
	{
		return _room;
	}
	
	public UserAccount get_reservationAccount() 
	{
		return _reservationAccount;
	}

	public void set_reservationAccount(UserAccount _reservationAccount) 
	{
		this._reservationAccount = _reservationAccount;
	}
	

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
