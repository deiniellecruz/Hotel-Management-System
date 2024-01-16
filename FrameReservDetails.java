import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class FrameReservDetails extends JFrame {
	private FrameMenu menuFrame;
	private Hotel hotel;
	private FrameReservDetails thisFrame;
	
	
	private JButton btnContinue, btnRoomSelect;
	private JSpinner spnrBedroom, spnrBathroom, spnrKitchen, spnrLivingRoom;
	private JComboBox cmbCheckIn, cmbCheckOut;
	private JTextField fldRoomNo;
	private JLabel lblStatus;
	private Room[] suitableRooms, availableRooms;
	
	private String guestName, guestAddress, guestContactNo;
	private int bedroom, bathroom, kitchen, livingroom;
	private CustomDate checkIn, checkOut;
	private Room room;
	
	public FrameReservDetails(
		FrameMenu menuFrame,
		String guestName,
		String guestAddress,
		String guestContactNo
	) {
		setTitle("Reservation Details");
		setSize(800, 250);
		
		this.menuFrame = menuFrame;            // This will refer to the JFrame of the main menu.
		hotel          = menuFrame.getHotel(); // This will refer to the hotel object
		thisFrame      = this;                 // This will refer to this frame
		
		// These are data passed from the guest information frame
		this.guestName = guestName;
		this.guestAddress = guestAddress;
		this.guestContactNo = guestContactNo;
		
		JPanel pnlDivider    = new JPanel(new GridLayout(1, 2));
		
		JPanel pnlRoom       = new JPanel(new BorderLayout());
		JPanel pnlRoomFields = new JPanel(new GridLayout(5, 1));
		JPanel pnlRoomLabels = new JPanel(new GridLayout(5, 1));
		
		// This is the status message at the bottom
		lblStatus = new JLabel();
		lblStatus.setHorizontalAlignment(JLabel.CENTER);
		lblStatus.setVerticalAlignment(JLabel.CENTER);
		lblStatus.setForeground(Color.red);
		
		// Inputs for room details
		/*
			Creates the JSpinners with their respective limits
			e.g. The bedroom spinner will have its limit from 1 to the maxBedroom from the hotel.
		*/
		spnrBedroom    = new JSpinner(new SpinnerNumberModel(1, 1, hotel.getMaxBedroom(), 1));
		spnrBathroom   = new JSpinner(new SpinnerNumberModel(1, 1, hotel.getMaxBedroom(), 1));
		spnrKitchen    = new JSpinner(new SpinnerNumberModel(0, 0, 1, 1));
		spnrLivingRoom = new JSpinner(new SpinnerNumberModel(0, 0, 1, 1));
		pnlRoomFields.add(spnrBedroom);
		pnlRoomFields.add(spnrBathroom);
		pnlRoomFields.add(spnrKitchen);
		pnlRoomFields.add(spnrLivingRoom);
		// Labels for room details
		pnlRoomLabels.add(new JLabel("Bedrooms: "));	
		pnlRoomLabels.add(new JLabel("Bathrooms: "));
		pnlRoomLabels.add(new JLabel("Kitchen: "));
		pnlRoomLabels.add(new JLabel("Living Room: "));
		
		pnlRoom.add(new JLabel("Room Accomodations"), BorderLayout.NORTH);
		pnlRoom.add(pnlRoomFields);
		pnlRoom.add(pnlRoomLabels, BorderLayout.WEST);
		
		
		// 
		JPanel pnlSchedule = new JPanel(new BorderLayout());
		JPanel pnlScheduleFields = new JPanel(new GridLayout(5, 1));
		JPanel pnlScheduleLabels = new JPanel(new GridLayout(5, 1));
		
		// Inputs for schedule details
		// Combo boxes for check-in and check-out
		cmbCheckIn = new JComboBox();
		cmbCheckIn.addItem(null); // The first choice is blank or null
		// This adds choices for the check-in date starting from the currentDate
		CustomDate tempDate = menuFrame.getCurrentDate();
		for (int i = 0; i < 50; i++) {
			cmbCheckIn.addItem(tempDate);
			tempDate = tempDate.next();
		}
		cmbCheckOut = new JComboBox();
		cmbCheckOut.setEnabled(false);
		fldRoomNo = new JTextField();
		fldRoomNo.setDisabledTextColor(Color.black);
		fldRoomNo.setEnabled(false);
		btnRoomSelect = new JButton("Select Room");
		pnlScheduleFields.add(cmbCheckIn);
		pnlScheduleFields.add(cmbCheckOut);
		pnlScheduleFields.add(fldRoomNo);
		pnlScheduleFields.add(btnRoomSelect);
		
		// Labels for schedule details
		pnlScheduleLabels.add(new JLabel("Check-in: "));
		pnlScheduleLabels.add(new JLabel("Check-out: "));
		pnlScheduleLabels.add(new JLabel("Room no.: "));
		
		pnlSchedule.add(new JLabel("Schedule"), BorderLayout.NORTH);
		pnlSchedule.add(pnlScheduleFields);
		pnlSchedule.add(pnlScheduleLabels, BorderLayout.WEST);
		
		
		pnlDivider.add(pnlRoom);
		pnlDivider.add(pnlSchedule);
		
		// Panel for the cancel/continue buttons
		JPanel pnlButton = new JPanel(new GridLayout(1, 2));
		JButton btnCancel = new JButton("Cancel");
		btnContinue       = new JButton("Continue");
		btnCancel  .addActionListener(new cancelAction());
		btnContinue.addActionListener(new continueAction());
		pnlButton.add(btnCancel);
		pnlButton.add(btnContinue);
		
		btnRoomSelect.addActionListener(new roomSelectAction());
		
		spnrBedroom   .addChangeListener(new updateChange());
		spnrBathroom  .addChangeListener(new updateChange());
		spnrKitchen   .addChangeListener(new updateChange());
		spnrLivingRoom.addChangeListener(new updateChange());
		
		cmbCheckIn .addActionListener(new cmbCheckInAction());
		cmbCheckOut.addActionListener(new cmbCheckOutAction());
		
		add(pnlDivider, BorderLayout.NORTH);
		add(lblStatus);
		add(pnlButton, BorderLayout.SOUTH);
		
		checkDetails();
	}
	
	private class cancelAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Sets menu frame location to this frame
			menuFrame.setLocationRelativeTo(thisFrame);
			
			// Closes this frame
			thisFrame.dispose();
			
			// Shows the main menu frame
			menuFrame.setVisible(true);
		}
	}
	
	public class cmbCheckInAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			CustomDate checkIn  = (CustomDate) cmbCheckIn.getSelectedItem();
			
			if (checkIn == null) {
				// If check-in is null, there must be no choices for check-out.
				cmbCheckOut.removeAllItems();
				cmbCheckOut.setEnabled(false);
			} else {
				// This sets the choices for the check-out date starting from the check-in date
				cmbCheckOut.removeAllItems();
				CustomDate tempDate = checkIn;
				for (int i = 0; i < 30; i++) {
					cmbCheckOut.addItem(tempDate);
					tempDate = tempDate.next();
				}
				cmbCheckOut.setEnabled(true);
			}
			checkDetails();
		}
	}
	
	public class cmbCheckOutAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			checkDetails();
		}
	}
		
	public class updateChange implements ChangeListener {
		public void stateChanged(ChangeEvent  ce) {
			checkDetails();
		}
	}
	
	private void checkDetails() {
		bedroom    = (Integer) spnrBedroom.getValue();
		bathroom   = (Integer) spnrBathroom.getValue();
		kitchen    = (Integer) spnrKitchen.getValue();
		livingroom = (Integer) spnrLivingRoom.getValue();
		
		checkIn  = (CustomDate) cmbCheckIn.getSelectedItem();
		checkOut = (CustomDate) cmbCheckOut.getSelectedItem();
		
		suitableRooms = hotel.findSuitableRooms(bedroom, bathroom, kitchen, livingroom);
		
		if (checkOut == null) {
			// If there is no check-out date, we only check for suitable rooms
			
			/* 
				The returned array has an initial length. To know if it's empty,
				we just have to test if the first item is null.
			*/
			if (suitableRooms[0] == null) {
				lblStatus.setText("*No suitable rooms found.*");
			} else {
				lblStatus.setText("*Suitable rooms found.*");
			}
			
			fldRoomNo.setText("");           // Reset room selection
			btnRoomSelect.setEnabled(false); // Disable room selection
			btnContinue.setEnabled(false);   // Disable continue button
		} else {
			availableRooms = hotel.findAvailableRooms(suitableRooms, checkIn, checkOut);
			
			if (availableRooms[0] == null) {
				lblStatus.setText("*No available rooms found.*");
				room = null;                     // Reset room selection
				fldRoomNo.setText("");           // Reset room field
				btnRoomSelect.setEnabled(false); // Disable room selection
			} else {
				lblStatus.setText("*Available rooms found.*");
				// Tests if a room is selected
				if (room == null) {
					fldRoomNo.setText("");         // Reset room field
					btnContinue.setEnabled(false); // Disable continue button
				} else {
					if ( // Tests if the room is still suitable and available
						room.checkAccommodations(bedroom, bathroom, kitchen, livingroom) >= 0 &&
						hotel.checkAvailability(room, checkIn, checkOut)
					) {
						fldRoomNo.setText(Integer.toString(room.getRoomNo())); // Sets the room no field
						btnContinue.setEnabled(true);                          // Enable continue button
					} else {
						room = null;                   // Reset room selection
						fldRoomNo.setText("");         // Reset room field
						btnContinue.setEnabled(false); // Disable continue button
					}
				}
				
				btnRoomSelect.setEnabled(true); // Enable room selection
			}
		}
	}
	
	public class roomSelectAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Creates a new room selection dialog with this frame as its parent
			JDialog roomSelectDialog = new DialogRoomSelection(
				thisFrame,
				availableRooms,
				bedroom,
				bathroom,
				kitchen,
				livingroom
			);
			
			roomSelectDialog.setVisible(true);
		}
	}
		
	public void selectRoom (Room room) {
		this.room = room; // sets the room
		checkDetails();
	}
	
	public class continueAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Creates a reservation confirmation frame
			JDialog reservConfirmFrame = new DialogReservConfirm(
				thisFrame,
				guestName,
				guestAddress,
				guestContactNo,
				bedroom,
				bathroom,
				kitchen,
				livingroom,
				room,
				checkIn,
				checkOut
			);
			
			reservConfirmFrame.setVisible(true);
		}
	}
	
	public void setReservation () {
		// Create the reservation and store it in a variable
		Reservation newReservation = hotel.createReservation(
			guestName,
			guestAddress,
			guestContactNo,
			room,
			checkIn,
			checkOut
		);
		
		// Get the reference no.
		int referenceNo = newReservation.getReferenceNo();
		
		// Sets menu frame location to this frame
		menuFrame.setLocationRelativeTo(thisFrame);
		
		// Closes this frame
		thisFrame.dispose();
		
		// Shows the main menu frame
		menuFrame.setVisible(true);
		
		JLabel label1 = new JLabel("The reference no. of your reservation is:", JLabel.CENTER);
		JLabel label2 = new JLabel(Integer.toString(referenceNo), JLabel.CENTER);
		label2.setFont(new Font("Arial", Font.BOLD, 16));
		
		// Display the reference no. in a dialog
		JOptionPane.showMessageDialog(
			menuFrame,
			new Object[] {
				label1, label2
			},
			"Reservation Set",
			JOptionPane.PLAIN_MESSAGE
		);
		
		
	}
}