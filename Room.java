
public class Room {
	
	enum RoomType {
		  Economic,
		  Luxurious
		}
	
	private int _roomNumber;
	private double _roomPrice;
	private RoomType _roomType;

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
	

}
