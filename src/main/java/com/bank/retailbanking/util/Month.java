package com.bank.retailbanking.util;

import java.util.Calendar;

public class Month {
	static int monthInt;
	
	

	private Month() {
		super();
	}



	public static int monthStringToInt(String month) {
		if (month.equalsIgnoreCase("january")) {
			monthInt = Calendar.JANUARY;
		}
		if (month.equalsIgnoreCase("february")) {
			monthInt = Calendar.FEBRUARY;
		}
		if (month.equalsIgnoreCase("march")) {
			monthInt = Calendar.MARCH;
		}
		if (month.equalsIgnoreCase("april")) {
			monthInt = Calendar.APRIL;
		}
		if (month.equalsIgnoreCase("may")) {
			monthInt = Calendar.MAY;
		}
		if (month.equalsIgnoreCase("june")) {
			monthInt = Calendar.JUNE;
		}
		if (month.equalsIgnoreCase("july")) {
			monthInt = Calendar.JULY;
		}
		if (month.equalsIgnoreCase("august")) {
			monthInt = Calendar.AUGUST;
		}
		if (month.equalsIgnoreCase("september")) {
			monthInt = Calendar.SEPTEMBER;
		}
		if (month.equalsIgnoreCase("october")) {
			monthInt = Calendar.OCTOBER;
		}
		if (month.equalsIgnoreCase("november")) {
			monthInt = Calendar.NOVEMBER;
		}
		if (month.equalsIgnoreCase("december")) {
			monthInt = Calendar.DECEMBER;
		}

		return monthInt+1;
	}

}
