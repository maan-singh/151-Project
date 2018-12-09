
import java.util.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ManagerView
 * @author TeamVoid
 * View meant for manager user accounts to view additional information and have additional functionality.
 */
public class ManagerView extends JFrame {
	// width of text area
	private final int WIDTH_TEXTAREA = 25;
	// height of text area
	private final int HEIGHT_TEXTAREA = 40;
	// height of each cell in the calendar
	private final int CALENDAR_CELL_HEIGHT = 50;
	// width of frame
	private final int WIDTH = 925;
	// width of height
	private final int HEIGHT = 575;
	// array of strings representing all the months of the year
	private final String[] MONTHS = { 
			"January","February","March","April","May","June","July","August","September","October","November","December"};
	// array od strings of all days in a week
	private final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	// the number of days in a week
	private final int DAYS_IN_WEEK = 7;
	// the number of weeks in a month
	private final int WEEKS_IN_MONTH = 6;
	// a reservation system object
	private ReservationSystem _reservationSystem;
	// JLabel for month and year (calendar)
	private JLabel monthYearJLabel;
	// JLabel for room numbers
	private JLabel roomNumbersJLabel;
	// JTextArea for calendar information
	private JTextArea calendarInformationJTextArea;
	// JTextArea for rooms information
	private JTextArea roomsInformationJTextArea;
	// JButton to navigate to previous month
	private JButton previousMonthJButton; 
	// JButton to navigate to next month
	private JButton nextMonthJButton;
	// JButton to navigate to previous year
	private JButton previousYearJButton; 
	// JButton to navigate to next year
	private JButton nextYearJButton;
	// JButton to save
	private JButton saveJButton;
	// JButton to load
	private JButton loadJButton;
	// Scroll Pane for Calendar
	private JScrollPane calendarJScrollPane; 
	// Scroll Pane for Rooms
	private JScrollPane roomsJScrollPane;
	// JPanel to paint a calendar
	private JPanel calendarJPanel;
	// JPanel to store buttons of calendar
	private JPanel calendarButtonsJPanel; 
	// JPanel to store calendar information
	private JPanel calendarInformationJPanel; 
	// JPanel to store rooms
	private JPanel roomsJPanel;
	// JPanel to store room information
	private JPanel roomsInformationJPanel;
	
	private DefaultTableModel calendarTableModel;
	private JTable calendarJTable;
	private JList<Integer> roomsJList;
	private JTabbedPane managerTabbedPane;

	
	int selectedCalendarRow;
	int selectedCalendarColumn;

	/**
	 * ManagerView
	 * @param reservationSystem
	 * View for the manager, that contains views and functionality that only the manager has access to
	 */
	public ManagerView(ReservationSystem reservationSystem) 
	{
		this._reservationSystem = reservationSystem;
		
		ChangeListener changeListener = new ChangeListener() 
		{
			public void stateChanged(ChangeEvent event) 
			{
				updateTableModel();
				selectCell();
				updateCalendarLabels();
				updateCalendar();
				updateRoomsInformationJTextArea();
			}
		};
		
		_reservationSystem.addListener(changeListener);
		
		setTitle("Manager View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		managerTabbedPane = new JTabbedPane();
		calendarJPanel = new JPanel(new BorderLayout());
		monthYearJLabel = new JLabel();
		
		updateCalendarLabels();
		
		createCalendarTableModel();
		createCalendarJPanel();
		
		calendarInformationJPanel = new JPanel(new BorderLayout());
		
		calendarInformationJTextArea = new JTextArea(WIDTH_TEXTAREA, HEIGHT_TEXTAREA);
		saveJButton = new JButton("Save everything!");
		saveJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
				_reservationSystem.saveReservations();
				JOptionPane.showMessageDialog(ManagerView.this, "Your save was successful", "The reservations were saved", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		loadJButton = new JButton("Load Information");
		loadJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
				try {
					_reservationSystem.loadReservations();
					JOptionPane.showMessageDialog(ManagerView.this, "Loading the reservations was successful", "The reservations were loaded", JOptionPane.PLAIN_MESSAGE);
				}
				catch(ClassNotFoundException notFoundException) 
				{
					JOptionPane.showMessageDialog(ManagerView.this, "Loading the reserversations was not successful", "The file was not found", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex) 
				{
					JOptionPane.showMessageDialog(ManagerView.this, "Loading the reservations was not successful", "There was an unknown error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		calendarInformationJPanel.add(loadJButton, BorderLayout.NORTH);
		calendarInformationJPanel.add(calendarInformationJTextArea, BorderLayout.CENTER);
		calendarInformationJPanel.add(saveJButton, BorderLayout.SOUTH);
		calendarJPanel.add(calendarInformationJPanel, BorderLayout.EAST);
		
		roomsJPanel = new JPanel(new BorderLayout());

		createRoomsListPanel();

		roomsInformationJPanel = new JPanel(new BorderLayout());
		
		roomsInformationJTextArea = new JTextArea(WIDTH_TEXTAREA, HEIGHT_TEXTAREA);
		
		roomsInformationJPanel.add(roomsInformationJTextArea, BorderLayout.CENTER);
		roomsJPanel.add(roomsInformationJPanel);
		
		managerTabbedPane.addTab("Calendar", calendarJPanel);
		managerTabbedPane.addTab("Rooms", roomsJPanel);
		
		add(managerTabbedPane);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	/**
	 * createRoomsListPanel
	 * 
	 * Method that creates the list panel for the rooms
	 */
	private void createRoomsListPanel() 
	{
		roomNumbersJLabel = new JLabel("Room Information by Room Number:");
		
		ListSelectionListener listSelectionListener = new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent listSelectionEvent) 
			{
				updateRoomsInformationJTextArea();
			}
		};
		
		Integer[] numberOfRooms = new Integer[ReservationSystem.TOTAL_ROOMS];
		
		for(int i = 0; i < numberOfRooms.length; i++) 
		{
			numberOfRooms[i] = i + 1;
		}
		
		JList roomsJList = new JList(numberOfRooms);
		
		roomsJList.addListSelectionListener(listSelectionListener);
		
		roomsJScrollPane = new JScrollPane(roomsJList);
		
		roomsJPanel.add(roomNumbersJLabel, BorderLayout.NORTH);
		roomsJPanel.add(roomsJScrollPane, BorderLayout.WEST);
	}
	
	/**
	 * updateRoomsInformationJTextArea
	 * 
	 * Method that updates the room information text area with new information
	 */
	private void updateRoomsInformationJTextArea() 
	{
		ArrayList<Reservation> reservations = _reservationSystem.getReservationsByRoomNumber(roomsJList.getSelectedIndex());
		
		String reservationList = "";
		for(Reservation reservation : reservations) 
		{
			reservationList = reservationList + reservation.toString() + "\n";
		}
		roomsInformationJTextArea.setText(reservationList);
	}
	
	/**
	 * createCalendarJPanel
	 * 
	 * Method that creates the panel containing the calendar information
	 */
	private void createCalendarJPanel() 
	{
		previousMonthJButton = new JButton("--");
		nextMonthJButton = new JButton("++");
		previousYearJButton = new JButton("-");
		nextYearJButton = new JButton("+");
		
		previousMonthJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				_reservationSystem.previousMonth();
			}
		});
		
		nextMonthJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
				_reservationSystem.nextMonth();
			}
		});
		
		previousYearJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
				_reservationSystem.previousYear();
			}
		});
		
		nextYearJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent actionEvent) 
			{
				_reservationSystem.nextYear();
			}
		});
		
		calendarJScrollPane = new JScrollPane(calendarJTable);
		
		calendarButtonsJPanel = new JPanel();
		calendarButtonsJPanel.add(previousYearJButton);
		calendarButtonsJPanel.add(previousMonthJButton);
		calendarButtonsJPanel.add(nextMonthJButton);
		calendarButtonsJPanel.add(nextYearJButton);
		
		calendarJPanel.add(monthYearJLabel, BorderLayout.NORTH);
		calendarJPanel.add(calendarJScrollPane, BorderLayout.CENTER);
		calendarJPanel.add(calendarButtonsJPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * createCalendarTableModel
	 * 
	 * Method that creates the calendar model to be used within the calendar panels
	 */
	private void createCalendarTableModel() 
	{
		MouseAdapter mouseAdapter = new MouseAdapter() 
		{
			public void mousePressed(MouseEvent mouseEvent) 
			{
				selectedCalendarRow = calendarJTable.getSelectedRow();
				selectedCalendarColumn = calendarJTable.getSelectedColumn();
				
				int day = (int) calendarJTable.getValueAt(selectedCalendarRow, selectedCalendarColumn);
				_reservationSystem.goToDate(_reservationSystem.getCalendar().get(Calendar.MONTH), day, _reservationSystem.getCalendar().get(Calendar.YEAR));
				
				updateCalendar();
			}
		};
		
		calendarTableModel = new DefaultTableModel(createMonthArray(), DAYS);
		
		calendarJTable = new JTable(calendarTableModel) 
		{
			public boolean isCellEditable(int row, int col) 
			{
				return false;
			}
		};
		
		calendarJTable.setRowHeight(CALENDAR_CELL_HEIGHT);
		calendarJTable.setCellSelectionEnabled(true);
		calendarJTable.addMouseListener(mouseAdapter);
		
		selectCell();
	}
	
	/**
	 * createMonthArray
	 * @return
	 * Method that returns the month and day 
	 */
	private Integer[][] createMonthArray() 
	{
		Integer[][] temp = new Integer[WEEKS_IN_MONTH][DAYS_IN_WEEK];
		
		int month = _reservationSystem.getCalendar().get(Calendar.MONTH);
		int year = _reservationSystem.getCalendar().get(Calendar.YEAR);
		
		Calendar tempCal = Calendar.getInstance();
		
		tempCal.set(year, month, 1);
		
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
		
		int day = 1;
		
		for(int i = 0; i < WEEKS_IN_MONTH; i++) 
		{
			for(int j = 0; j < DAYS_IN_WEEK; j++) 
			{
				if((i != 0 || j >= firstDay) && (day <= tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)))
				{
					temp[i][j] = day++;
				}
					
					
				if(day == _reservationSystem.getCalendar().get(Calendar.DAY_OF_MONTH) + 1) 
				{
					selectedCalendarRow = i;
					selectedCalendarColumn = j;
				}
			}
		}
		return temp;
	}
	
	/**
	 * updateCalendar
	 * 
	 * Method that updates the calendar based on the current date
	 */
	private void updateCalendar() 
	{
		Date currentDate = _reservationSystem.getCalendar().getTime();
		ArrayList<Reservation> reservations = _reservationSystem.getReservationsByDate(currentDate);
		
		String reserationList = "";
		for(Reservation reservation : reservations) 
		{
			reserationList = reserationList + reservation.toString() + "\n";
		}
		calendarInformationJTextArea.setText(reserationList);
	}
	
	/**
	 * updateCalendarLabels
	 * 
	 * Method that updates the labels used for the calendar
	 */
	private void updateCalendarLabels() 
	{
		monthYearJLabel.setText(MONTHS[_reservationSystem.getCalendar().get(Calendar.MONTH)] + " " + _reservationSystem.getCalendar().get(Calendar.YEAR));
	}
	
	/**
	 * updateTableModel
	 * 
	 * Method that updates the calendar table model with newly created calendar table model 
	 */
	private void updateTableModel() 
	{
		Integer[][] temp = createMonthArray();
	
		calendarTableModel = new DefaultTableModel(temp, DAYS);
		calendarJTable.setModel(calendarTableModel);
	}
	
	/**
	 * selectCell
	 * 
	 * Method that shows the selected cell.
	 */
	private void selectCell() 
	{
		for(int i = 0; i < calendarTableModel.getRowCount(); i++) 
		{
			for(int j = 0; j < calendarTableModel.getColumnCount(); j++) 
			{
				Integer val = (Integer) calendarTableModel.getValueAt(i, j);
				
				if(val != null && val == _reservationSystem.getCalendar().get(Calendar.DAY_OF_MONTH))
				{
					calendarJTable.changeSelection(i, j, false, false);
				}
			}
		}
	}
}