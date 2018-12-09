import java.util.Date;

public class Reservation implements java.io.Serializable 
{
	private Date _checkInDate; 
	private Date _checkOutDate;
	private Room _room;
	private Account _reservationAccount;
	
	public Reservation(Date checkInDate, Date checkOutDate, Room room) throws Exception 
	{
		this._room = room;
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
	}
	
	public Reservation(Date checkInDate, Date checkOutDate, Room room, Account reservationAccount) throws Exception 
	{
		this._checkInDate = checkInDate;
		this._checkOutDate = checkOutDate;
		this._room = room;
		this.set_reservationAccount(reservationAccount);
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
		
		return "Check-In Date: " + _checkInDate.toString() + " | Check-Out Date: " + _checkOutDate.toString() + " | Room Number: " + _room.getNiceRoomNumber() + " |";
	}

	public Account get_reservationAccount() 
	{
		return _reservationAccount;
	}

	public void set_reservationAccount(Account _reservationAccount) 
	{
		this._reservationAccount = _reservationAccount;
	}
}
