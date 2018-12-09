/**
 * Class to depict a room in the hotel.
 * @author TeamVoid
 */
public class Room {
	
	/**
	 * Enum declaration to differentiate between types of rooms available in the hotel. Options are Economic and Luxurious.
	 * @author TeamVoid
	 */
	enum RoomType {
		  Economic,
		  Luxurious
		}
	
	private int _roomNumber; // the room number of the room (ranging from 1-20)
	private double _roomPrice; // the price of the room (for Economic - $100 & for Luxurious - $300 per night)
	private RoomType _roomType; // the type or room. Economic or Luxurious.
	
	/**
	 * Constructor for a Room with a room number and type specified.
	 * @param RoomNum the number of the room (1-20)
	 * @param RoomType the type of room (Economic or Luxurious)
	 */
	public Room(int RoomNum, RoomType RoomType)
	{
		_roomNumber = RoomNum;
		_roomType = RoomType;
		if(_roomType == Room.RoomType.Luxurious)
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