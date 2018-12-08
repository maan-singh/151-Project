package hotelsystem;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class Reservation 
{
	private Date checkIn;
	private Date checkOut;
	private char roomType;
	private int roomNum;
	private Guest guest;
	
	//private Account reservingAccount;
	
	private SimpleDateFormat formattor;
	
	
	//No reservation
	public Reservation(Date in, Date out, char roomType, int roomNum)
	{
		this.checkIn = in;
		this.checkOut = out;
		this.roomType = roomType;
		this.roomNum = roomNum;
		
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	
	//Reserved with an account
	public Reservation(Date in, Date out, char roomType, int roomNum, Guest reservedGuest)
	{
		this.checkIn = in;
		this.checkOut = out;
		this.roomType = roomType;
		this.roomNum = roomNum;
		this.guest = reservedGuest;
		
		
		formattor = new SimpleDateFormat("MM/DD/YYYY");
	}
	
	public Date getCheckIn()
	{
		return checkIn;
	}
	
	public Date getCheckOut()
	{
		return checkOut;
	}
	
	public int getRoomNumber()
	{
		return roomNum;
	}
	
	public char getRoomType()
	{
		return roomType;
	}
	
	public boolean conflict(Date boolIn, Date boolOut)
	{
		if((checkIn.compareTo(boolIn) < 0 && checkOut.compareTo(boolIn) > 0) || (checkIn.compareTo(boolOut) < 0 && checkOut.compareTo(boolOut) > 0))
			return true;
		return false;
	}
	
	public String toString()
	{
		return " Check In Date: " + formattor.format(checkIn) + "\n" + 
			   " Check Out Date: " + formattor.format(checkOut) + "\n" +
			   " Room Number: " + getRoomNumber();
	}
	

}
