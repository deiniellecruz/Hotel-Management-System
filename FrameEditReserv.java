import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameEditReserv extends JFrame {
	private FrameMenu menuFrame;
	private Hotel hotel;
	private JFrame thisFrame;
	private Reservation reservation;
	
	private JButton btnCancelReserv;
	private JLabel lblCancelled;
	
	public FrameEditReserv(FrameMenu menuFrame, Reservation reservation) {
		setTitle("Guest Information");
		setSize(500, 230);
		
		this.menuFrame = menuFrame;   // This will refer to the JFrame of the main menu
		hotel = menuFrame.getHotel(); // This is the hotel object
		thisFrame = this;
		this.reservation = reservation;
		
		// This is the title "Reservation Info"
		JLabel lblTitle = new JLabel("Reservation Details", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		
		// This is the panel for the reservation details
		JPanel pnlDetails = new JPanel(new GridLayout(8,1));
		pnlDetails.add(new JLabel("Guest Name: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(reservation.getGuestName()));
		pnlDetails.add(new JLabel("Guest Address: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(reservation.getGuestAddress()));
		pnlDetails.add(new JLabel("Guest Contact No.: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(reservation.getGuestContactNo()));
		pnlDetails.add(new JLabel("Check-in: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(reservation.getCheckIn().toString()));
		pnlDetails.add(new JLabel("Check-out: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(reservation.getCheckOut().toString()));
		pnlDetails.add(new JLabel("Room No.: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(Integer.toString(reservation.getRoom().getRoomNo())));
		pnlDetails.add(new JLabel("Price: ", JLabel.RIGHT));
		pnlDetails.add(new JLabel(Double.toString(reservation.getPrice())));
		pnlDetails.add(new JLabel("Cancellation: ", JLabel.RIGHT));
		// This is the label for cancellation
		lblCancelled = new JLabel("");
		lblCancelled.setFont(new Font("Arial", Font.BOLD, 12));
		pnlDetails.add(lblCancelled);
		
		
		// Panel for the buttons
		JPanel pnlButton         = new JPanel(new GridLayout(1, 2));
		JButton btnBack          = new JButton("Back");
		        btnCancelReserv  = new JButton("Cancel My Reservation");
		btnBack        .addActionListener(new backAction());
		btnCancelReserv.addActionListener(new cancelReservAction());
		pnlButton.add(btnBack);
		pnlButton.add(btnCancelReserv);
		
		// If the reservation is already cancelled, the button for cancelling must be disabled
		if (reservation.isCancelled()) {
			btnCancelReserv.setEnabled(false);
			lblCancelled.setText("Cancelled");
			lblCancelled.setForeground(Color.red);
		} else {
			lblCancelled.setText("Not Cancelled");
			lblCancelled.setForeground(Color.green);
		}
		
		add(lblTitle, BorderLayout.NORTH);
		add(pnlDetails);
		add(pnlButton, BorderLayout.SOUTH);
	}
	
	private class backAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Sets menu frame location to this frame
			menuFrame.setLocationRelativeTo(thisFrame);
			
			// Closes this frame
			thisFrame.dispose();
			
			// Shows the main menu frame
			menuFrame.setVisible(true);
		}
	}
	
	private class cancelReservAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			hotel.cancelReservation(reservation);
			
			btnCancelReserv.setEnabled(false);
			lblCancelled.setText("Cancelled");
			lblCancelled.setForeground(Color.red);
			
			lblCancelled.revalidate();
			lblCancelled.repaint();
			
			JOptionPane.showMessageDialog(
				thisFrame,
				"This reservation has been cancelled.",
				"Reservation Cancelled",
				JOptionPane.PLAIN_MESSAGE
			);
		}
	}
}