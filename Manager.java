package hotelsystem;

public class Manager extends User
{
	private Reservation reserve;

	public Manager(String name, String password, int id) 
	{
		super(name, password, id);
	}
	
	//manager can load existing reservations, view information, save
	//reservations, and quit the system. 
	
	public Reservation loadExistingReservatoins()
	{
		return reserve;
	}
	
	public String viewInformation()
	{
		return "";
	}
	
	public void  saveReservation()
	{
		 
	}
	
	public void quit()
	{
		
	}

}
