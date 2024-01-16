public class CustomDate {
	private final int day, month, year;
	
	public CustomDate(int month, int day, int year) {
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("Invalid Argument: month can only be 1-12");
		}
		if (day < 1) {
			throw new IllegalArgumentException("Invalid Argument: day cannot be less than 1");
		}
		if (day > numberOfDaysInMonth(month, year)) {
			throw new IllegalArgumentException("Invalid Argument: day cannot be higher than the days in the given month");
		}
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String toString() {
		return "" + month + "/" + day + "/" + year;
	}
	
	private static int numberOfDaysInMonth(int month, int year) {
		switch (month) {
			case 4: case 6: case 9: case 11:
				return 30; // Apr, June, Sept, and Nov have 30 days
			case 2:
				if ((year%4 == 0 && year%100 != 0) || year%400 == 0) {
					return 29; // Feb has 29 days in a leap year
				}
				return 28; // Feb has 28 days in a non-leap year
			default:
				return 31; // Jan, Mar, May, July, Aug, Oct, and Dec has 31 days
		}
	}
	
	private static int numberOfDaysInYear(int year) {
		if ((year%4 == 0 && year%100 != 0) || year%400 == 0) {
			return 366; // leap year has 366 days
		}
		return 365; // leap year has 365 days
	}
	
	public int compareTo(CustomDate date2) { // compares two dates
		int day2 = date2.day, month2 = date2.month, year2 = date2.year;
		
		int difference;
		
		difference = year - year2;
		if (difference == 0) { // if the years are the same
			difference = month - month2;
			if (difference == 0) { // if the months are the same
				difference = day - day2;
			}
		}
		
		// if date2 comes after this, difference will be negative
		
		return difference;
	}
	
	public static int compare(CustomDate date1, CustomDate date2) {
		return date1.compareTo(date2);
	}
	
	public static int getDuration(CustomDate date1, CustomDate date2) {
		int comparison = date1.compareTo(date2);
		if (comparison == 0) {
			return 1;
		} else if (comparison > 0) {
			CustomDate date3 = date1;
			date1 = date2;
			date2 = date3;
		}
		int days = 0;
		int day2 = date2.day, month2 = date2.month, year2 = date2.year;
		int day1 = date1.day, month1 = date1.month, year1 = date1.year;
		
		if (year1 == year2) {
			if (month1 == month2) {
				return day2 - day1 + 1; 
			}
			for (int i = month1; i < month2; i++) {
				days += numberOfDaysInMonth(i, year1);
			}
			days += -day1 + 1 + day2;
		} else {
			for (int i = month1; i <= 12; i++) {
				days += numberOfDaysInMonth(i, year1);
			}
			days += -day1 + 1;
			
			for (int i = month2 - 1; i >= 1; i--) {
				days += numberOfDaysInMonth(i, year2);
			}
			days += day2;
			
			for (int i = year1 + 1; i < year2; i++) { 
				days += numberOfDaysInYear(i);
			}
		}
		
		return days;
	}
	
	public CustomDate next() {
		int day2 = day + 1;
		int month2 = month;
		int year2 = year;
		
		if (day2 > numberOfDaysInMonth(month, year)) {
			day2 = 1;
			month2++;
		}
		if (month > 12) {
			month2 = 1;
			year2++;
		}
		
		return new CustomDate(month2, day2, year2);
	}
}