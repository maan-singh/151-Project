package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Tester for the Hotel System
 * 
 * @author TeamVoid
 */
public class Tester 
{	
	
	/**
	 * Creates a login frame at the start of the program
	 * @param model of the MVC
	 * @param guest user Guest type
	 * @param manager user manager Type
	 */
	private static void loginFrame(ReservationSystem model, GuestView guest, ManagerView manager) 
	
	{
		final int FIELD_WIDTH = 5;
		//final int ID = 0;
		
		//JComponents
		JFrame loginFrame = new JFrame(); 
		JTextField field_SignUp = new JTextField(FIELD_WIDTH);
		JLabel label_SignUp = new JLabel("Enter Your User Name");
		
		//JButtons for guest and manager
		JButton guest_Button = new JButton("Guest");
		JButton manager_Button = new JButton("Manager");
		
		//FlowLayout for the frame
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//When guest is chosen as a user
		guest_Button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				String[] optionsToSign = { "Sign up", "Sign in" }; //options to begin with
				
				Object[] collection = { label_SignUp, field_SignUp };
				
				int input = JOptionPane.showOptionDialog(loginFrame, collection, "Guest login", 
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionsToSign, null);
				
				
				if(input == 0) //Sign Up
				{ 
					String userName = field_SignUp.getText();
					UserAccount newAccount = new UserAccount(userName, "abc123", UserAccountType.Guest);
					
					
					model.addAccount(newAccount);
					guest.setActiveAccount(newAccount);
					
					guest.setVisible(true);
				}
				else if (input == 1) //Sign In
				{
					try 
					{
						int user_ID = Integer.parseInt(field_SignUp.getText());
						
						guest.setActiveAccount(user_ID);
						guest.setVisible(true);
					}
					catch(Exception ex) 
					{
						JOptionPane.showMessageDialog(loginFrame, "User ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}field_SignUp.setText("");
			}
		});
		
		//When Manager is chosen as user
		manager_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				manager.setVisible(true);
			}
		});
	
		//Adding the buttons to the frame
		loginFrame.add(manager_Button);
		loginFrame.add(guest_Button);
		loginFrame.setVisible(true);
	}
	
	/**
	 * Main function to test out the hotel system
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		ReservationSystem model = new ReservationSystem();
		GuestView guest = new GuestView(model);
		ManagerView manager = new ManagerView(model);
		
		loginFrame(model, guest, manager);
	}
}
