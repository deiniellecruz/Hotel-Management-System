public class Reservation {
	private Room room;
	private int referenceNo;
	private String guestName, guestAddress, guestContactNo;
	private CustomDate checkIn, checkOut;
	private double price;
	private boolean cancelled;
	
	public Reservation(String guestName, String guestAddress, String guestContactNo, Room room, CustomDate checkIn, CustomDate checkOut, int referenceNo) {
		this.guestName = guestName;
		this.guestAddress = guestAddress;
		this.guestContactNo = guestContactNo;
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.referenceNo = referenceNo;
		price = room.getRate() * (CustomDate.getDuration(checkIn, checkOut));
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public String getGuestName() {
		return guestName;
	}
	
	public String getGuestAddress() {
		return guestAddress;
	}
	
	public String getGuestContactNo() {
		return guestContactNo;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public CustomDate getCheckIn() {
		return checkIn;
	}
	
	public CustomDate getCheckOut() {
		return checkOut;
	}
	
	public int getReferenceNo() {
		return referenceNo;
	}
	
	public double getPrice() {
		return price;
	}
	
}

