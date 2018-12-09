package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import project.UserAccount.UserAccountType;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Guest View class when user selects to be a guest.
 * Can make, view, and cancel reservations
 * 
 * @author TeamVoid
 */
public class GuestView extends JFrame 
{
	
	
	private final int ROWS = 4;
	private final int FIELD_WIDTH = 5;
	private final int CELL_HEIGHT = 10;
	
	private final int WIDTH = 500;
	private final int HEIGHT = 400;
	
	private final int TEXT_AREA_WIDTH = 30;
	private final int TEXT_AREA_HEIGHT = 20;
	
	
	private UserAccount user;
	
	//Model
	private ReservationSystem _reservationSystem;
	
	Room newRoom;
	private int rowOfSelecRoom;
	private int rowOfSelectCal;
	private int colOfSelectCal;
	
	private Date currentCheckInDate;
	private Date currentCheckOutDate;
	private SimpleDateFormat formattor;

	String currCheckIn = formattor.format(currentCheckInDate);  
	String currCheckOut = formattor.format(currentCheckOutDate);  
	
	private boolean[] occupiedRooms;
	

	//JButtons
	private JButton confirm_Button;
	private JButton cancel_Button;
	private JButton make_Button;
	

	//Panels
	private JPanel button_Panel;
	private JPanel roomNumber_Panel;
	private JTabbedPane guest_Tab;
	private JPanel res_Panel;
	private JPanel cancel_Panel;
	
	//JLabels
	private JLabel allReservations_Label;
	private JLabel availableRooms_Label;
	private JLabel roomNum_Label;
	
	
	//JText 
	private JTextField checkIn_Field;
	private JTextField checkOut_Field;
	private JTextArea availableRoomsArea;
	private JTextField roomNumber_Field;
	
	private DefaultListModel<Reservation> viewModel;
	private JList<Reservation> viewList;
	private JComboBox<String> roomTypeBox;
	
	

	public GuestView(ReservationSystem model) throws Exception {
		this._reservationSystem = model;
		
		user = new UserAccount("guest1", "abc123", UserAccountType.Guest);
		
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Guest");
		this.setLayout(new FlowLayout());
		
		res_Panel = new JPanel(new BorderLayout());
		guest_Tab = new JTabbedPane();
		
		button_Panel = new JPanel();
		availableRoomsArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		res_Panel.add(availableRoomsArea, BorderLayout.CENTER);
		
		checkIn_Field = new JTextField(FIELD_WIDTH);
		checkOut_Field = new JTextField(FIELD_WIDTH);
		
		viewModel = new DefaultListModel<Reservation>();
		viewList = new JList<Reservation>(viewModel);
		
		updateViewCancelModel();
		
		availableRooms_Label = new JLabel("Available rooms");
		res_Panel.add(availableRooms_Label, BorderLayout.NORTH);
		
		String[] types = { "Luxurious", "Economic" };
		roomTypeBox = new JComboBox<>(types);
		
		confirm_Button = new JButton("Confirm?");
		make_Button = new JButton("Make a reservation");
		
		makeViewCancelTab();
		
		
		
		make_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Object[] collection = {"Enter check-in date:", checkIn_Field, "Enter check-out date:", checkOut_Field, "Enter room type:", roomTypeBox};
				
				int option = JOptionPane.showConfirmDialog(GuestView.this, collection, "Enter dates", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) 
				{
					boolean[] bool;
					String roomType = (roomTypeBox.getSelectedIndex() == 0) ? "Luxirious" : "Economy";
					String checkIn = checkIn_Field.getText();
					String checkOut = checkOut_Field.getText();
					
					try 
					{
						if(model.canStay(currentCheckInDate, currentCheckOutDate))
								throw new Exception();
								
						bool = model.getFilledRooms(currentCheckInDate, currentCheckOutDate);
						availableRoomsArea.setText(printAvailableRooms(bool, roomType));
						occupiedRooms = bool;
						
						
						currCheckIn = checkIn;
						currCheckOut = checkOut;
					}
					
					
					catch(Exception exception) 
					{
						JOptionPane.showMessageDialog(GuestView.this, " Enter dates to make a reservation", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					
					
					checkIn_Field.setText("");
					checkOut_Field.setText("");
				}
			}
		});
		
		confirm_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				int newNum = newRoom.getNiceRoomNumber();
				
				try 
				{
					newNum = Integer.parseInt(roomNumber_Field.getText());
					
					
					if(!occupiedRooms[newNum]) 
					{
						String typeOfRoom = (newNum > ReservationSystem.TOTAL_ROOMS / 2) ? "Economy" : "Luxurious";
						
						try 
						{
							Reservation reservation = new Reservation(currentCheckInDate, currentCheckOutDate, newRoom);
							
							user.addReservation(reservation);
							model.notifyListeners();
							
							updateViewCancelModel();
							String[] options = { "Simple Receipt", "Comprehensive Receipt" };
							
							String input = (String) JOptionPane.showInputDialog(null, "Choose the type of Receipt", "Receipt Type", JOptionPane.QUESTION_MESSAGE, null,  options, options[1]); 
							
							
							Receipt receipt;
							
							
							//Selection for Receipt type
							if (input.equals("Simple Receipt")){
								receipt = new SimpleReceipt();
								JOptionPane.showMessageDialog(GuestView.this, receipt.printReceipt(user) + "\n", "Reservation Made!", JOptionPane.PLAIN_MESSAGE);
							
							}
								
							else{	
								receipt = new ComprehensiveReceipt();
								JOptionPane.showMessageDialog(GuestView.this, receipt.printReceipt(user) + "\n", "Reservation Made!", JOptionPane.PLAIN_MESSAGE);
							}
							
						}
						
						
						
						catch(Exception exception) 
						{
							JOptionPane.showMessageDialog(GuestView.this, "Error", "Reservation can not be made", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
				
				catch(NullPointerException nullException) 
				{
					JOptionPane.showMessageDialog(GuestView.this, "Error", "No reservations found", JOptionPane.WARNING_MESSAGE);
				}
				
				
				catch(NumberFormatException nullFormat) 
				{
					JOptionPane.showMessageDialog(GuestView.this, "Enter valid selection", "Invalid Room", JOptionPane.WARNING_MESSAGE);
				}
				

				availableRoomsArea.setText("");
				roomNumber_Field.setText("");
				
				occupiedRooms = null;
			}
		});
		
		
		guest_Tab.addTab("New reservation", res_Panel);
		guest_Tab.addTab("View/Cancel", cancel_Panel);
		
		
		button_Panel.add(make_Button);
		button_Panel.add(confirm_Button);
		res_Panel.add(button_Panel, BorderLayout.SOUTH);
		
		
		roomNumber_Field = new JTextField(FIELD_WIDTH);
		roomNum_Label = new JLabel("Enter room number you want to reserve:");
		roomNumber_Panel = new JPanel();
		
		roomNumber_Panel.add(roomNum_Label);
		roomNumber_Panel.add(roomNumber_Field);
		res_Panel.add(roomNumber_Panel, BorderLayout.EAST);
				
		this.add(guest_Tab);
		
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	/**
	 * Updates View/Cancel Tab's Reservation List when changes made to Model
	 */
	public void updateViewCancelModel() {
		
		viewModel.clear();
		// Get activeAccount's Reservations
		ArrayList<Reservation> viewReservationsAL = user.getListOfReservations();
		
		// Add activeAccount's Reservations to viewReservationsModel
		for(int i = 0; i < viewReservationsAL.size(); i++) {
			viewModel.addElement(viewReservationsAL.get(i));
		}	
	}
	
	
	/**
	 * Represents the View/Cancel tab 
	 */
	private void makeViewCancelTab() 
	{
		viewList = new JList<Reservation>(viewModel);
		JScrollPane viewReservationsScrollPane = new JScrollPane(viewList);
		cancel_Panel = 	new JPanel(new BorderLayout());
		cancel_Button = new JButton("Cancel selected reservation");
		allReservations_Label = new JLabel("All reservations");
		
				
		// When clicked, removes selected Reservations from activeAccount's Reservations		
		cancel_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] cancelIndices = viewList.getSelectedIndices();				
				user.removeReservations(cancelIndices);
				
				updateViewCancelModel();
				_reservationSystem.notifyListeners();
			}
		});
		
		// Add components to viewCancelPanel
		cancel_Panel.add(viewReservationsScrollPane, BorderLayout.WEST);
		cancel_Panel.add(allReservations_Label, BorderLayout.NORTH);
		cancel_Panel.add(cancel_Button, BorderLayout.SOUTH);
	}
	
	
	
	/**
	 * Sets the active account once a user signs up.
	 * 
	 * @param account The currently focused account
	 */
	public void setActiveAccount(UserAccount account) {
		user = account;
		availableRoomsArea.setText("Current user: " + user.getName());
		updateViewCancelModel();
	}
	
	/**
	 * Sets the active account by ID once a user signs in.
	 * 
	 * @param account The currently focused account
	 */
	public void setActiveAccount(int id) {
		user = _reservationSystem.getAccounts().get(id);
		availableRoomsArea.setText("Current user: " + user.getName());
		updateViewCancelModel();
	}
	
	private String printAvailableRooms(boolean[] rooms, String roomType) {
		String roomsList = "";
		int startingIndex, endingIndex;
		
		if(roomType.equalsIgnoreCase("L")) {
			startingIndex = 0;
			endingIndex = rooms.length / 2;
		}
		else {
			startingIndex = rooms.length / 2;
			endingIndex = rooms.length;
		}
		
		for(int i = startingIndex; i < endingIndex; i++) {
			if(!rooms[i]) {
				roomsList += "Room " + (i + 1) + "\n";
			}
		}
		
		if(roomsList.equals(""))
			return "There are no such rooms available";
		
		return roomsList;
	}
	
}
