public class Room {
	private final int bedroom, bathroom, kitchen, livingroom, roomNo;
	private final double rate;
	
	public Room(int bedroom, int bathroom, int kitchen, int livingroom, double rate, int roomNo) {
		this.bedroom = bedroom;
		this.bathroom = bathroom;
		this.kitchen = kitchen;
		this.livingroom = livingroom;
		this.rate = rate;
		this.roomNo = roomNo;
	}
	
	public int getBedroom() {
		return bedroom;
	}
	
	public int getBathroom() {
		return bathroom;
	}
	
	public int getKitchen() {
		return kitchen;
	}
	
	public int getLivingroom() {
		return livingroom;
	}
	
	public double getRate() {
		return rate;
	}
	
	public int getRoomNo() {
		return roomNo;
	}
	
	public int checkAccommodations(int bedroom, int bathroom, int kitchen, int livingroom) {
		/*
			-1 - smaller room (has less accomodities than needed)
			0  - exactly similar room (has the needed accomodities in exactly the same numbers)
			1  - bigger room (has more accomodities than needed)
		*/
		
		if (
			this.bedroom < bedroom ||
			this.bathroom < bathroom ||
			this.kitchen < kitchen ||
			this.livingroom < livingroom
		) {
			return -1;
		} else if (
			this.bedroom == bedroom &&
			this.bathroom == bathroom &&
			this.kitchen == kitchen &&
			this.livingroom == livingroom
		) {
			return 0;
		}
		return 1;
	}
}