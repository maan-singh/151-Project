import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.*;
import java.io.*;


public class ReservationSystem 
//implements java.io.Serializable 
{
	public static final int TOTAL_ROOMS = 20;
	public static final int MILLISECONDS = 1000;
	public static final int SECONDS = 60;
	public static final int MINUTES = 60;
	public static final int HOURS = 24;

	private Date dateCurrent;
	private Calendar cal;
	private Date dateSelected;
	private ArrayList<UserAccount> userAccounts;
	private ArrayList<ChangeListener> listeners;
	
	public ReservationSystem() throws Exception 
	{
		userAccounts = new ArrayList<UserAccount>();
		listeners = new ArrayList<ChangeListener>();
		//setCalendar(); //INITCALENDAR
		cal = Calendar.getInstance();	
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dateCurrent = cal.getTime();
		setDateSelected(dateCurrent);
	}
	
//	private void setCalendar() 
//	{
//
//	}
	public Calendar getCalendar() 
	{ 
		return cal; 
	}
	
	public Date getDateSelected() {
		return dateSelected;
	}
	
	public void setDateSelected(Date d) {
		dateSelected = d;
	}
	
	public ArrayList<UserAccount> getAccounts() 
	{ 
		return userAccounts; 
	}
	
	
	public void addListener(ChangeListener listener) 
	{ 
		listeners.add(listener); 
	}
	
	public void addAccount(UserAccount account) 
	{
		userAccounts.add(account);
		notifyListeners();
	}
	
	public void notifyListeners() 
	{
		ChangeEvent event = new ChangeEvent(this);	
		for(ChangeListener cl:listeners) {
			cl.stateChanged(event);
		}
	}

	
	public void goToCurrent() 
	{
		cal = Calendar.getInstance();	
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dateCurrent = cal.getTime();
		setDateSelected(dateCurrent);
	}
	
	public static int[] parseDatetoInt(String date) 
	{
		String[] arrayOfStrings=date.split("/");
		int[] parsedDate = new int[arrayOfStrings.length];
		
		for (int i=0; i < parsedDate.length; i++) {
			if (i == 0) {
				parsedDate[0] = Integer.parseInt(arrayOfStrings[0]) - 1;
			} else {
				parsedDate[i] = Integer.parseInt(arrayOfStrings[i]);
			}
		}
		
//		parsedDate[0] = Integer.parseInt(temp[0]) - 1;
//		parsedDate[1] = Integer.parseInt(temp[1]);
//		parsedDate[2] = Integer.parseInt(temp[2]);
			
		return parsedDate;
	}
	
	public void goToDate(String date) 
	{
		int[] parsedDate = parseDatetoInt(date);
		cal.set(parsedDate[2], parsedDate[0], parsedDate[1]);
		
		setDateSelected(cal.getTime());
	}

	public void goToDate(int month, int day, int year) 
	{
		cal.set(year, month, day);
		setDateSelected(cal.getTime());
	}
	
	

	public void previousYear() 
	{
		cal.add(Calendar.YEAR, -1);
		setDateSelected(cal.getTime());
		notifyListeners();
	}
	
	public void nextYear() 
	{
		cal.add(Calendar.YEAR, 1);
		setDateSelected(cal.getTime());
		notifyListeners();
	}
	
	public void previousMonth() 
	{
		cal.add(Calendar.MONTH, -1);
		setDateSelected(cal.getTime());
		notifyListeners();
	}
	
	public void nextMonth() 
	{
		cal.add(Calendar.MONTH, 1);
		setDateSelected(cal.getTime());
		notifyListeners();
	}


	public boolean[] getFilledRooms(Date checkInD, Date checkOutD) throws Exception 
	{
		boolean[] availableRooms = new boolean[TOTAL_ROOMS]; 
		Date checkIn = checkInD;
		Date checkOut = checkOutD;
	
		for (UserAccount acc: userAccounts) { 
			for (Reservation reservation : acc.getListOfReservations()) {
				if(reservation.checkConflict(checkIn, checkOut)) {
					availableRooms[reservation.getRoom().getRoomNumber()] = true;
				} else {
					availableRooms[reservation.getRoom().getRoomNumber()] = false;
				}
			}
		}
		return availableRooms;
	}

	public UserAccount getAccountByID(int id) {
		return userAccounts.get(id);
	}
	
	public void removeReservation(Reservation res) {
		for(UserAccount acc:userAccounts) {
			for(Reservation r:acc.getListOfReservations()) {
				if(r.equals(res))
					acc.removeReservation(r);
			}
		}
		notifyListeners();
	}
	
	public ArrayList<Reservation> getReservationsByRoomNumber(int num) {
		ArrayList<Reservation> reservationsByRoomNumber= new ArrayList<Reservation>();
		for(UserAccount acc:userAccounts) {
			for(Reservation r:acc.getListOfReservations()) {
				if(r.getRoom().getRoomNumber() == num)
					reservationsByRoomNumber.add(r);
			}
		}	
		return reservationsByRoomNumber;
	}
	
	public ArrayList<Reservation> getReservationsByDate(Date d) {
		ArrayList<Reservation> reservationsByDate= new ArrayList<Reservation>();	
		for(UserAccount acc:userAccounts) {
			for(Reservation r:acc.getListOfReservations()) {
				if(d.compareTo(r.getCheckOutDate()) <= 0 && d.compareTo(r.getCheckInDate()) >= 0) {
					reservationsByDate.add(r);
				}
			}
		}
		return reservationsByDate;
	}
	
	public ArrayList<Reservation> getReservations() {
		ArrayList<Reservation> allReservations= new ArrayList<Reservation>();
		for(UserAccount acc:userAccounts) {
			for(Reservation r:acc.getListOfReservations()) {
				allReservations.add(r);
			}
		}
		return allReservations;
	}
	
	//check validity of stay
	public boolean canStay(Date checkInD,Date checkOutD) {
		Date checkInDate = null;
		Date checkOutDate = null;
		
		try {
			checkInDate = checkInD;
			checkOutDate = checkOutD;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date today = new Date();
		
		int differenceTime=(int)(checkOutDate.getTime()-checkInDate.getTime());
		int differenceDays=(int)((differenceTime)/(MILLISECONDS*SECONDS*MINUTES*HOURS));	
		if(differenceDays>=60) {
			return false;
		}
		if(today.before(checkOutDate) || today.before(checkInDate)){
			return false;
		}
		return true;
	}
	
	public void loadReservations() throws IOException, ClassNotFoundException {
         FileInputStream f=new FileInputStream("reservation.ser");
         ObjectInputStream in=new ObjectInputStream(f);
         this.userAccounts=(ArrayList<UserAccount>) in.readObject();
         in.close();
         f.close();
         System.out.printf("DATA LOADED. \n");
	}
	
	public void saveReservations() {
		try {
	         FileOutputStream f=new FileOutputStream("reservation.ser");
	         ObjectOutputStream out=new ObjectOutputStream(f);
	         out.writeObject(this.userAccounts);
	         out.close();
	         f.close();
	         System.out.printf("DATA SAVED. \n");
	    }
		catch(IOException ioException) 
		  {
			ioException.printStackTrace();
	      }
	}	
}
