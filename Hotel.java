import java.util.Random;

public class Hotel {
	private Reservation[] reservArray;
	private Room[] roomArray;
	private int reservCount, roomCount;
	private int maxBedroom, maxBathroom;
	private Random r;
	
	public Hotel(Random r) {
		reservArray = new Reservation[8];
		reservCount = 0;
		roomArray = new Room[8];
		roomCount = 0;
		
		/* 
			maxBedroom and maxBathroom will store the greatest number of
			bedrooms and bathrooms (respectively) that the rooms have.
			
			These will only be used for the min and max for the spinner inputs.
		*/
		
		maxBedroom = 0;
		maxBathroom = 0;
		
		this.r = r;
	}
	
	public int getMaxBedroom() {
		return maxBedroom;
	}
	
	public int getMaxBathroom() {
		return maxBathroom;
	}
	
	public void createRoom(int bedroom, int bathroom, int kitchen, int livingroom, double rate) {
		// extend the array if full
		if (roomCount >= roomArray.length) {
			Room[] temp = new Room[roomArray.length*2];
			for (int i = 0; i < roomCount; i++) {
				temp[i] = roomArray[i];
			}
			roomArray = temp;
		}
		
		if (bedroom > maxBedroom) {
			maxBedroom = bedroom;
		}
		
		if (bathroom > maxBathroom) {
			maxBathroom = bathroom;
		}
		
		roomArray[roomCount] = new Room(bedroom, bathroom, kitchen, livingroom, rate, roomCount+1);
		
		roomCount++;
	}
	
	public Room fetchRoom(int roomNo) {
		if (roomNo <= roomCount) {
			return roomArray[roomNo-1];
		}
		return null;
	}
	
	public Reservation createReservation(String guestName, String guestAddress, String guestContactNo, Room room, CustomDate checkIn, CustomDate checkOut) {
		int referenceNo = 0;
		
		// generates a 7 digit int unique from other reservations
		while (true) {
			boolean taken = false;
			referenceNo = r.nextInt(9000000) + 1000000;
			for (Reservation reservation: reservArray) {
				if (reservation == null) {
					break;
				}
				if (reservation.getReferenceNo() == referenceNo) {
					taken = true;
					break;
				}
			}
			if (!taken) {
				break;
			}
		}
		
		// extend the array if full
		if (reservCount >= reservArray.length) {
			Reservation[] temp = new Reservation[reservArray.length*2];
			for (int i = 0; i < reservCount; i++) {
				temp[i] = reservArray[i];
			}
			reservArray = temp;
		}
		
		Reservation newReservation = new Reservation(guestName, guestAddress, guestContactNo, room, checkIn, checkOut, referenceNo);
		
		reservArray[reservCount] = newReservation;
		
		reservCount++;
		
		return newReservation;
	}
	
	public void cancelReservation(Reservation reservation) {
		reservation.cancel();
	}
	
	public Reservation fetchReservation(int referenceNo) {
		for (Reservation reservation: reservArray) {
			if (reservation == null) {
				break;
			}
			if (reservation.getReferenceNo() == referenceNo) {
				return reservation;
			}
		}
		return null;
	}
	
	public Room[] findSuitableRooms(int bedroom, int bathroom, int kitchen, int livingroom) {
		Room[] suitableRooms = new Room[8];
		int suitableRoomCount = 0;
		
		for (Room room: roomArray) {
			if (room == null) {
				break;
			}
			if (room.checkAccommodations(bedroom, bathroom, kitchen, livingroom) >= 0) {
				// for extending the array
				if (suitableRoomCount >= suitableRooms.length) {
					Room[] temp = new Room[suitableRooms.length*2];
					for (int i = 0; i < suitableRoomCount; i++) {
						temp[i] = suitableRooms[i];
					}
					suitableRooms = temp;
				}
				
				suitableRooms[suitableRoomCount] = room;
				suitableRoomCount++;
			}
		}
		
		return suitableRooms;
	}
	
	public boolean checkAvailability(Room room, CustomDate checkIn, CustomDate checkOut) {
		int roomNo = room.getRoomNo();
		
		for (Reservation reservation: reservArray) {
			if (reservation == null) {
				break;
			}
			if (!reservation.isCancelled() && reservation.getRoom().getRoomNo() == roomNo) {
				CustomDate reservIn = reservation.getCheckIn();
				CustomDate reservOut = reservation.getCheckOut();
				
				if (reservIn.compareTo(checkOut) <= 0 && reservOut.compareTo(checkIn) >= 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Room[] findAvailableRooms(Room[] suitableRooms, CustomDate checkIn, CustomDate checkOut) {
		Room[] availableRooms = new Room[8];
		int availableRoomCount = 0;
		
		for (Room room: suitableRooms) {
			if (room == null) {
				break;
			}
			if (checkAvailability(room, checkIn, checkOut)) {
				// for extending the array
				if (availableRoomCount >= availableRooms.length) {
					Room[] temp = new Room[availableRooms.length*2];
					for (int i = 0; i < availableRoomCount; i++) {
						temp[i] = availableRooms[i];
					}
					availableRooms = temp;
				}
				
				availableRooms[availableRoomCount] = room;
				availableRoomCount++;
			}
		}
		
		return availableRooms;
	}
}