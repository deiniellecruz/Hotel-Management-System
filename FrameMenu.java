import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameMenu extends JFrame {
	private FrameMenu menuFrame;
	private Hotel hotel;
	private CustomDate currentDate; // This date is used for check-in dates selection.
	
	public FrameMenu(Hotel hotel, CustomDate currentDate) {
		setTitle("Main Menu");
		setSize(500, 200);
		this.hotel = hotel;
		this.currentDate = currentDate;
		// default layout of JFrame is BorderLayout
		
		this.menuFrame = this; // used to refer to the JFrame in scopes where `this` cannot refer to it
		
		JLabel lblTitle = new JLabel("A Very Creative Hotel Name", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
		add(lblTitle);
		
		JPanel pnlButton = new JPanel(new GridLayout(1, 2));
		
		JButton btnMakeReservation = new JButton("Make a Reservation");
		JButton btnEditReservation = new JButton("View/Edit my Reservation");
		btnMakeReservation.addActionListener(new makeReservationAction());
		btnEditReservation.addActionListener(new editReservationAction());
		pnlButton.add(btnMakeReservation);
		pnlButton.add(btnEditReservation);
		
		add(pnlButton, BorderLayout.SOUTH);
	}
	
	public CustomDate getCurrentDate() {
		return currentDate;
	}
	
	// This returns the hotel object
	public Hotel getHotel() {
		return hotel;
	}
	
	private class makeReservationAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			/*
				The menu frame will be passed to the construction of the guest info frame.
				This is to give access to the menu frame methods.
			*/
			
			// Creates a guest info frame
			JFrame guestInfoFrame = new FrameGuestInfo(menuFrame);
			guestInfoFrame.setLocationRelativeTo(menuFrame); // Sets location to the menu frame
			guestInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// Hides the main menu
			menuFrame.setVisible(false);
			
			guestInfoFrame.setVisible(true);
		}
	}
	
	
	
	
	
	
	private class editReservationAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				String input = JOptionPane.showInputDialog(menuFrame, "Type the reference no.");
				if (input != null) {
					int referenceNo = Integer.parseInt(input);
					Reservation reservation = hotel.fetchReservation(referenceNo);
					
					if (referenceNo < 1000000 || referenceNo > 9999999) {
						JOptionPane.showMessageDialog(menuFrame, "Reference no. should be 7-digits", "Invalid Input", JOptionPane.ERROR_MESSAGE);
					} else if (reservation == null) {
						JOptionPane.showMessageDialog(menuFrame, "Could not find a reservation with the reference no.", "Reservation not found", JOptionPane.ERROR_MESSAGE);
					} else {
						// Creates an edit reservation frame
						JFrame editReservFrame = new FrameEditReserv(menuFrame, reservation);
						editReservFrame.setLocationRelativeTo(menuFrame);
						editReservFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
						// hides the menu frame
						menuFrame.setVisible(false);
						
						editReservFrame.setVisible(true);
					}
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(menuFrame, "Reference no. should be a 7-digit number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	
}