import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.io.*;


public class ReservationSystem implements java.io.Serializable 
{
	public static final int NUMBER_OF_ROOMS = 20;
	
	private Calendar calendar;
	private Date currentDate;
	private Date selectedDate;
	private ArrayList<Account> accounts;
	private ArrayList<ChangeListener> listeners;
	
	public ReservationSystem() throws Exception 
	{
		accounts = new ArrayList<>();
		listeners = new ArrayList<>();
		initCalendar();
	}
	
	public ArrayList<Account> getAccounts() 
	{ 
		return accounts; 
	}
	
	public Calendar getCalendar() 
	{ 
		return calendar; 
	}
	
	public void addListener(ChangeListener cl) 
	{ 
		listeners.add(cl); 
	}
	
	public void addAccount(Account account) 
	{
		accounts.add(account);
		changeMade();
	}
	

	public boolean[] getOccupiedRooms(Date checkInDate, Date checkOutDate) throws Exception 
	{
		boolean[] availableRooms = new boolean[NUMBER_OF_ROOMS]; 
	
		Date checkIn = checkInDate;
		Date checkOut = checkOutDate;
	
		for(Account a : accounts) 
		{ 
			for(Reservation reservation : a.getReservations()) 
			{
				if(reservation.checkConflict(checkIn, checkOut)) 
				{
					availableRooms[reservation.getRoom().getRoomNumber()] = true;
				}
				else
				{
					availableRooms[reservation.getRoom().getRoomNumber()] = false;
				}
			}
		}
		
		return availableRooms;
	}

	public Account getAccountByID(int id) 
	{
		return accounts.get(id);
	}

	public void findAndRemoveReservation(Reservation toRemove) 
	{
		for(Account account : accounts) 
		{
			for(Reservation r : account.getReservations()) 
			{
				if(r.equals(toRemove))
					account.removeReservation(r);
			}
		}
		changeMade();
	}
	
	public ArrayList<Reservation> getReservationsByRoomNumber(int number) 
	{
		ArrayList<Reservation> temp = new ArrayList<>();
		
		for(Account account : accounts) 
		{
			for(Reservation reservation : account.getReservations()) 
			{
				if(reservation.getRoom().getRoomNumber() == number)
					temp.add(reservation);
			}
		}
		
		return temp;
	}
	
	
	public ArrayList<Reservation> getReservationsByDate(Date date) 
	{
		ArrayList<Reservation> temp = new ArrayList<>();
		
		for(Account account : accounts) 
		{
			for(Reservation reservation : account.getReservations()) 
			{
				if(date.compareTo(reservation.getCheckInDate()) >= 0 && date.compareTo(reservation.getCheckOutDate()) <= 0)
				{
					temp.add(reservation);
				}
			}
		}
		return temp;
	}
	
	public ArrayList<Reservation> getAllReservations() 
	{
		ArrayList<Reservation> temp = new ArrayList<>();
	
		for(Account account : accounts) {
			for(Reservation reservation : account.getReservations()) 
			{
				temp.add(reservation);
			}
		}
		return temp;
	}
	
	
	public boolean checkStayValidity(Date checkIn, Date checkOut) 
	{
		Date checkInDate = null;
		Date checkOutDate = null;
		
		try {
			checkInDate = checkIn;
			checkOutDate = checkOut;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		Date today = new Date();
		
		if(today.before(checkInDate) || today.before(checkOutDate))
		{
			return false;
		}
		
		int diffInDays = (int)( (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24) );
		
		if(diffInDays >= 60)
		{
			return false;
		}
		return true;
	}
	

	public void goToCurrent() 
	{
		initCalendar();
	}
	

	public void goToDate(String date) 
	{
		int[] parsed = parseDate(date);
		calendar.set(parsed[2], parsed[0], parsed[1]);
		
		selectedDate = calendar.getTime();
	}
	
	

	public void goToDate(int month, int day, int year) 
	{
		calendar.set(year, month, day);
		selectedDate = calendar.getTime();
	}

	
	public void nextMonth() 
	{
		calendar.add(Calendar.MONTH, 1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	

	public void previousMonth() 
	{
		calendar.add(Calendar.MONTH, -1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	

	public void nextYear() 
	{
		calendar.add(Calendar.YEAR, 1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	

	public void previousYear() 
	{
		calendar.add(Calendar.YEAR, -1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	

	public static int[] parseDate(String date) 
	{
		String[] temp = date.split("/");
		int[] parsed = new int[temp.length];
		
		parsed[0] = Integer.parseInt(temp[0]) - 1;
		parsed[1] = Integer.parseInt(temp[1]);
		parsed[2] = Integer.parseInt(temp[2]);
			
		return parsed;
	}
	
	public void changeMade() 
	{
		ChangeEvent event = new ChangeEvent(this);
		
		for(ChangeListener changeListener : listeners) {
			changeListener.stateChanged(event);
		}
	}
	

	private void initCalendar() 
	{
		calendar = Calendar.getInstance();
	
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		currentDate = calendar.getTime();
		selectedDate = currentDate;
	}
	

	public void load() throws IOException, ClassNotFoundException
	{
         FileInputStream fileIn = new FileInputStream("reservation.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         this.accounts = (ArrayList<Account>) in.readObject();
         in.close();
         fileIn.close();
         System.out.printf("Data has been loaded. \n");
	}

	
	public void save()
	{
		try {
	         FileOutputStream fileOut = new FileOutputStream("reservation.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.accounts);
	         out.close();
	         fileOut.close();
	         System.out.printf("Data has been saved. \n");
	    }
		catch(IOException ioException) 
		  {
			ioException.printStackTrace();
	      }
	}	
}
