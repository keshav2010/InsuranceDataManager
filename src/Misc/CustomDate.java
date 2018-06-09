package Misc;

import java.io.Serializable;

public class CustomDate implements Serializable{
	
	//acc. for months declared here for mantaining uniformity in application
	public static String stringJanuary = new String("JAN");
	public static String stringFebruary = new String("FEB");
	public static String stringMarch = new String("MAR");
	public static String stringApril = new String("APR");
	public static String stringMay = new String("MAY");
	public static String stringJune = new String("JUNE");
	public static String stringJuly = new String("JULY");
	public static String stringAugust = new String("AUG");
	public static String stringSeptember = new String("SEPT");
	public static String stringOctober = new String("OCT");
	public static String stringNovember = new String("NOV");
	public static String stringDecember = new String("DEC");
	
	private String day, month, year;
	
	
	public static String[] getMonths() {
		String[] months = {stringJanuary, stringFebruary, stringMarch, stringApril, stringMay,
				stringJune, stringJuly, stringAugust, stringSeptember, stringOctober, stringNovember, stringDecember
		};
		return months;
	}
	public CustomDate(String day, String month, String year){
		this.day= new String(day);
		this.month = new String(month);
		this.year = new String(year);
	}
	public CustomDate(CustomDate date) {
		this.day = new String(date.day);
		this.month = new String(date.month);
		this.year = new String(date.year);
	}
	public String getDateString() {
		return new String(day + month + year);
	}
	public void setDate(String day, String month, String year) {
		this.day = new String(day);
		this.month = new String(month);
		this.year = new String(year);
	}
	public boolean isEqualTo(CustomDate other) {
		return this.day.equals(other.day) && this.month.equals(other.month) && this.year.equals(other.year);	
	}

	//doesn't check validity for leap-years or exceptions like February currently.
	public boolean isValid() {
		try {
			int dayInteger = Integer.parseInt(this.day);
			if(dayInteger < 0 || dayInteger > 31)//not checking for february and leap-years
				return false;
			int yearInteger = Integer.parseInt(this.year);
			if(yearInteger < 1900)
				return false;
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
