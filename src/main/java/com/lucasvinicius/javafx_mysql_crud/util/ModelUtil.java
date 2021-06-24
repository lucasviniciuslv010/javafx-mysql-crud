package com.lucasvinicius.javafx_mysql_crud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ModelUtil {

	public static int calculateAge(String dateOfBirthStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dateOfBirthInput = null;

		try {
			dateOfBirthInput = sdf.parse(dateOfBirthStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date of birth.");
		}

		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(dateOfBirthInput);

		Calendar today = Calendar.getInstance();

		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		dateOfBirth.add(Calendar.YEAR, age);

		if (today.before(dateOfBirth))
			age--;

		return age;
	}
	
	public static String phoneFormat(String phone) {
		if (phone.length() < 11) 
			throw new IllegalArgumentException("Invalid phone number.");
		
		String formattedPhone = 
			"(" + phone.substring(0, 2) + ") " 
				+ phone.substring(2, 3) + " "
				+ phone.substring(3, 7) + " - " 
				+ phone.substring(7);
		return formattedPhone;
	}
	
	public static String contractNumberFormat(String contractNumber) {
		if (contractNumber.length() < 6) 
			throw new IllegalArgumentException("Invalid contract number.");
		
		String formattedContract = contractNumber.substring(0, 6) + " - " + contractNumber.substring(6);
		return formattedContract;
	}
	
	public static Integer tryParseInteger(String intStr) {
		try {
			int number = Integer.parseInt(intStr);
			return number;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Double tryParseDouble(String doubStr) {
		try {
			double number = Double.parseDouble(doubStr);
			return number;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Long tryParseLong(String longStr) {
		try {
			long number = Long.parseLong(longStr);
			return number;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
}
