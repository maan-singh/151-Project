package hotelsystem;

import java.util.ArrayList;


public class Guest extends User
{
	private ArrayList<Reservation> reservations;

	public Guest(String name, String password, int id) 
	{
		super(name, password, id);
		
		this.reservations = new ArrayList<Reservation>();
		
	}

	
	public void addReservation(Reservation res)
	{
		reservations.add(res);
	}
	
	public void removeReservation(Reservation res) {
		reservations.remove(res);
	}

	
	public String toString()
	{
		return "User: " + getName() + "\n" + "ID:" + getID();
	}
}
