import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogRoomSelection extends JDialog {
	private FrameReservDetails parent;
	private JDialog thisDialog;
	
	private Room[] availableRooms;
	private Room viewedRoom;
	private int bedroom, bathroom, kitchen, livingroom; // These are for the filtering of exactly similar rooms
	
	private JButton btnCancel, btnSelect;
	private JCheckBox chkShowExact;
	private JPanel pnlList, pnlInfo;
	private JScrollPane scpnList;
	
	public DialogRoomSelection (
		FrameReservDetails parent,
		Room[] availableRooms,
		int bedroom,
		int bathroom,
		int kitchen,
		int livingroom
	) {
		super(parent, "Room Selection", ModalityType.DOCUMENT_MODAL);
		setSize(600, 300);
		
		this.parent = parent; // This will refer to the parent (reservation details frame)
		thisDialog  = this;  // This will refer to this dialog
		
		// These are the available rooms found from the reservation details
		this.availableRooms = availableRooms;
		
		// These are the needed room accomodations based on the reservation details
		this.bedroom    = bedroom;
		this.bathroom   = bathroom;
		this.kitchen    = kitchen;
		this.livingroom = livingroom;
		
		// This is the center panel which will have the room list
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(Color.white);
		
		pnlList = new JPanel(); // This is the actual panel for the list
		pnlList.setBackground(Color.white);
		scpnList = new JScrollPane(pnlList); // Scroll pane for the list
		chkShowExact = new JCheckBox("Show only exactly similar rooms.");
		chkShowExact.addActionListener(new showExactAction());
		pnlCenter.add(chkShowExact, BorderLayout.NORTH);
		pnlCenter.add(scpnList);
		
		
		// This is the east panel which will display the room details
		JPanel pnlEast = new JPanel(new BorderLayout());
		
		JLabel lblEastTitle = new JLabel("   Room Information   ", JLabel.CENTER);
		lblEastTitle.setFont(new Font("Arial", Font.BOLD, 15));
		       pnlInfo   = new JPanel(new GridLayout(12, 1));
		JPanel pnlLabels = new JPanel(new GridLayout(12, 1));
		pnlLabels.add(new JLabel("   Room No.: ", JLabel.RIGHT));
		pnlLabels.add(new JLabel("   Bedrooms: ", JLabel.RIGHT));
		pnlLabels.add(new JLabel("   Bathrooms: ", JLabel.RIGHT));
		pnlLabels.add(new JLabel("   Kitchens: ", JLabel.RIGHT));
		pnlLabels.add(new JLabel("   Living Rooms: ", JLabel.RIGHT));
		pnlLabels.add(new JLabel("   Charge Rate: ", JLabel.RIGHT));
		
		pnlEast.add(pnlInfo);
		pnlEast.add(pnlLabels, BorderLayout.WEST);
		pnlEast.add(lblEastTitle, BorderLayout.NORTH);
		
		// Panel for the cancel/continue buttons
		JPanel pnlButton = new JPanel(new GridLayout(1, 2));
		btnCancel = new JButton("Cancel");
		btnSelect = new JButton("Select Room");
		btnCancel.addActionListener(new cancelAction());
		btnSelect.addActionListener(new selectAction());
		pnlButton.add(btnCancel);
		pnlButton.add(btnSelect);
		
		add(pnlCenter);
		add(pnlEast, BorderLayout.EAST);
		add(pnlButton, BorderLayout.SOUTH);
		
		updateList();
		
		setLocationRelativeTo(parent);
	}
	
	private class cancelAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Closes this dialog
			thisDialog.dispose();
		}
	}
	
	private class showExactAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			updateList();
		}
	}
	
	private void updateList() {
		boolean showOnlyExact = chkShowExact.isSelected();
		int roomCount = 0;
		
		pnlList.removeAll();
		for (Room tempRoom: availableRooms) {
			if (tempRoom == null) {
				break; // terminate the loop once it reaches null
			}
			// If showOnlyExact is false, the if statement will proceed.
			// If not, it will check if the room is exactly similar to the needed accommodations.
			if (
			    !showOnlyExact ||
			    tempRoom.checkAccommodations(bedroom, bathroom, kitchen, livingroom) == 0
			) {
				JPanel roomPanel = new JPanel(new BorderLayout());
				
				JLabel lblRoomTitle = new JLabel("Room #" + tempRoom.getRoomNo());
				lblRoomTitle.setFont(new Font("Arial", Font.BOLD, 16));
				roomPanel.add(lblRoomTitle, BorderLayout.NORTH);
				
				roomPanel.add(new JLabel(
					"Bedrooms: "     + tempRoom.getBedroom() + " | " +
					"Bathrooms: "    + tempRoom.getBathroom() + " | " +
					"Kitchens: "     + tempRoom.getKitchen() + " | " +
					"Living Rooms: " + tempRoom.getLivingroom() + " | " +
					"P"         + tempRoom.getRate() + "/day"
				));
				
				JButton viewRoomButton = new JButton(">> View Room >>");
				viewRoomButton.addActionListener(new viewRoomAction(tempRoom));
				roomPanel.add(viewRoomButton, BorderLayout.SOUTH);
				
				pnlList.add(roomPanel);
				roomCount++;
			}
		}
		// pnlList.setLayout(new GridLayout(roomCount, 1));
		pnlList.setLayout(new GridLayout(Math.max(roomCount, 3), 1));
		
		scpnList.revalidate();
		scpnList.repaint();
	}
	
	private void updateInfo() {
		if (viewedRoom == null) {
			pnlInfo.removeAll();
			// Disables the select room button
			btnSelect.setEnabled(false);
		} else {
			pnlInfo.removeAll();
			pnlInfo.add(new JLabel(Integer.toString(viewedRoom.getRoomNo())));
			pnlInfo.add(new JLabel(Integer.toString(viewedRoom.getBedroom())));
			pnlInfo.add(new JLabel(Integer.toString(viewedRoom.getBathroom())));
			pnlInfo.add(new JLabel(Integer.toString(viewedRoom.getKitchen())));
			pnlInfo.add(new JLabel(Integer.toString(viewedRoom.getLivingroom())));
			pnlInfo.add(new JLabel(Double.toString(viewedRoom.getRate())));
			// Disables the select room button
			btnSelect.setEnabled(true);
		}
		
		pnlInfo.revalidate();
		pnlInfo.repaint();
	}
	
	private class viewRoomAction implements ActionListener {
		private Room room;
		
		public viewRoomAction (Room room) {
			this.room = room;
		}
		
		public void actionPerformed(ActionEvent ae) {
			viewedRoom = room;
			updateInfo();
		}
	}
	
	private class selectAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Calls the method in the reservation details frame for setting the selected room
			parent.selectRoom(viewedRoom);
			
			// Closes this dialog
			thisDialog.dispose();
		}
	}
}