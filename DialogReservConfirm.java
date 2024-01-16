import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class DialogReservConfirm extends JDialog {
	private FrameReservDetails parent;
	private JDialog thisDialog;
	
	public DialogReservConfirm(
		FrameReservDetails parent,
		String guestName,
		String guestAddress,
		String guestContactNo,
		int bedroom,
		int bathroom,
		int kitchen,
		int livingroom,
		Room room,
		CustomDate checkIn,
		CustomDate checkOut
	) {
		super(parent, "Reservation Confirmation", ModalityType.DOCUMENT_MODAL);
		setSize(400, 200);
		
		this.parent = parent; // This will refer to the parent (reservation details frame)
		thisDialog  = this;   // This will refer to this dialog
		
		// This is the title "Reservation Info"
		JLabel lblTitle = new JLabel("Reservation Details", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		
		// This is the panel for the reservation details
		JPanel pnlDetails = new JPanel(new GridLayout(7,2));
		pnlDetails.add(new JLabel("Guest Name: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(guestName));
		pnlDetails.add(new JLabel("Guest Address: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(guestAddress));
		pnlDetails.add(new JLabel("Guest Contact No.: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(guestContactNo));
		pnlDetails.add(new JLabel("Check-in: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(checkIn.toString()));
		pnlDetails.add(new JLabel("Check-out: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(checkOut.toString()));
		pnlDetails.add(new JLabel("Room No.: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(Integer.toString(room.getRoomNo())));
		pnlDetails.add(new JLabel("Price: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(Double.toString(room.getRate()*CustomDate.getDuration(checkIn,checkOut))));
		
		// Panel for the back/confirm buttons
		JPanel pnlButton = new JPanel(new GridLayout(1, 2));
		JButton btnBack    = new JButton("Back");
		JButton btnConfirm = new JButton("Set Reservation");
		btnBack   .addActionListener(new backAction());
		btnConfirm.addActionListener(new confirmAction());
		pnlButton.add(btnBack);
		pnlButton.add(btnConfirm);
		
		add(lblTitle, BorderLayout.NORTH);
		add(pnlDetails);
		add(pnlButton, BorderLayout.SOUTH);
		
		setLocationRelativeTo(parent);
	}
	
	private class backAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Closes this dialog
			thisDialog.dispose();
		}
	}
	
	private class confirmAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Closes this dialog
			thisDialog.dispose();
			
			// Calls the method for setting the reservation
			parent.setReservation();
		}
	}
}