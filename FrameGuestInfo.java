import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameGuestInfo extends JFrame {
	private FrameMenu menuFrame;
	private JFrame thisFrame;
	private JTextField fldGuestName, fldGuestAddress, fldGuestContactNo;
	
	public FrameGuestInfo(FrameMenu menuFrame) {
		setTitle("Guest Information");
		setSize(500, 150);
		
		// This will refer to the JFrame of the main menu
		this.menuFrame = menuFrame;
		thisFrame = this;
		
		// pnlLabels contains the input labels
		JPanel pnlLabels = new JPanel(new GridLayout(4, 1));
		pnlLabels.add(new JLabel("Full Name: "));
		pnlLabels.add(new JLabel("Address: "));
		pnlLabels.add(new JLabel("Contact No.: "));
		
		// pnlFields contains the input fields
		JPanel pnlFields = new JPanel(new GridLayout(4, 1));
		fldGuestName      = new JTextField();
		fldGuestAddress   = new JTextField();
		fldGuestContactNo = new JTextField();
		pnlFields.add(fldGuestName);
		pnlFields.add(fldGuestAddress);
		pnlFields.add(fldGuestContactNo);
		
		// Panel for the cancel/continue buttons
		JPanel pnlButton = new JPanel(new GridLayout(1, 2));
		JButton btnCancel   = new JButton("Cancel");
		JButton btnContinue = new JButton("Continue");
		btnCancel  .addActionListener(new cancelAction());
		btnContinue.addActionListener(new continueAction());
		pnlButton.add(btnCancel);
		pnlButton.add(btnContinue);
		
		add(pnlFields);
		add(pnlLabels, BorderLayout.WEST);
		add(pnlButton, BorderLayout.SOUTH);
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
	
	private class continueAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// gets the values in the guest info inputs			
			String guestName = fldGuestName.getText();
			String guestAddress = fldGuestAddress.getText();
			String guestContactNo = fldGuestContactNo.getText();
			
			if (
				guestName.length() == 0 ||
				guestAddress.length() == 0 ||
				guestContactNo.length() == 0
			) {
				JOptionPane.showMessageDialog(thisFrame, "Fields must not be empty", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			} else {
				// Creates a reservation details frame
				JFrame reservDetailsFrame = new FrameReservDetails(
					menuFrame,
					guestName,
					guestAddress,
					guestContactNo
				);
				
				reservDetailsFrame.setLocationRelativeTo(thisFrame); // Sets location to this frame
				reservDetailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Closes this guest info frame
				thisFrame.dispose();
				
				reservDetailsFrame.setVisible(true);
			}
		}
	}
}