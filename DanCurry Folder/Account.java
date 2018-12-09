import java.util.ArrayList;

public class Account implements java.io.Serializable 
{
	private static int nextID = 0;
	private int id;
	private String name;
	private ArrayList<Reservation> reservations;
	
	public void setName(String name) 
	{ 
		this.name = name; 	
	}
	
	public int getID()
	{ 
		return id; 			
	}
	
	public String getName() 						
	{ 
		return name; 			
	}
	
	public ArrayList<Reservation> getReservations() 
	{ 
		return reservations;  
	}
	
	public Account(String name) 
	{
		setName(name);
		reservations = new ArrayList<>();
		generateID();
	}

	public String getRoomsReserved()
	{
		String roomsList = "";
		for (int i = 0; i < this.reservations.size();i++)
		{
			roomsList += reservations.get(i).getRoom().getNiceRoomNumber() + "\n";
		}
		
		return roomsList;
		
	}
	

	public void generateID() 
	{
		id = nextID;
		nextID++;
	}
	

	public void addReservation(Reservation r) 
	{
		reservations.add(r);
	}
	
	public void removeReservation(Reservation r) 
	{
		reservations.remove(r);
	}
	

	public void removeReservations(int[] removeIndices) 
	{
		
		ArrayList<Reservation> cancelThese = new ArrayList<Reservation>();
		
		for(int i = 0; i < removeIndices.length; i++) {
			cancelThese.add(reservations.get(removeIndices[i]));
		}
		
		for(Reservation r : cancelThese) {
			reservations.remove(r);
		}
	}
	

	public int getTotalBalance()
	{
		int totalBalance = 0;
		for (Reservation reservation : this.reservations)
		{
			if (reservation.getRoom().getRoomType() == Room.RoomType.Luxurious)
			{
				totalBalance += 200;
			}
			if (reservation.getRoom().getRoomType() == Room.RoomType.Economic)
			{
				totalBalance += 80;
			}
			
		}
		return totalBalance;
	}
	
	public String toString() {
		return "Account Name: " + name + " ID: " + id;
	}	
}
