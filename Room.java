package hotelsystem;

public class Room {
	
	enum RoomType {
		  Economic,
		  Luxurious
		}
	
	private int _roomNumber;
	private double _roomPrice;
	private RoomType _roomType;
	private boolean isReserved;

	public Room(int RoomNum, RoomType RoomType)
	{
		_roomNumber = RoomNum;
		_roomType = RoomType;
		if(_roomType == RoomType.Luxurious)
		{
			_roomPrice = 300.00;
		}
		else
		{
			_roomPrice = 100.00;
		}
		
		this.isReserved = false;
	}
	
	public int getRoomNumber() 
	{
		return _roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) 
	{
		this._roomNumber = roomNumber;
	}
	
	public int getNiceRoomNumber()
	{
		return (_roomNumber + 1);
	}
	
	public RoomType getRoomType() 
	{
		return this._roomType;
	}
	
	public void setRoomType(RoomType RoomType) 
	{
		this._roomType = RoomType;
	}
	
	public double getRoomPrice() 
	{
		return _roomPrice;
	}
	
	
	/**
	 * Reserves the room, changes the isReserved status to true
	 */
	public void reserveRoom()
	{
		this.isReserved = true;
	}
	
	/**
	 * Checks if the room is reserved or not
	 * @return boolean true or false
	 */
	public boolean isReserved()
	{
		if (isReserved  == true)
			return true;
		return false;
	}
	

}
