package fr.excilys.cdb.dto.validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.dto.AddComputerFormOutput;
import fr.excilys.cdb.exception.CustomDateException;
import fr.excilys.cdb.exception.EmptyError;
import fr.excilys.cdb.exception.ParseError;

public class ValidatorAddComputer {
	/*
	 * ------------------------------------------ 
	 * | 				Test Fcs				|
	 * ------------------------------------------
	 */
	
	public static void validate(AddComputerFormOutput addComputerFormOutput) throws ParseError, EmptyError, CustomDateException {
		validateComputerName(addComputerFormOutput.getComputerName());
		validateDiscontinuedDateAfterIntroducedDate(addComputerFormOutput.getIntroducedDate(), addComputerFormOutput.getDiscontinuedDate());
	}
	
	private static void validateComputerName(String computerName) throws EmptyError {
		if(computerName.isEmpty()) {
			throw new EmptyError();
		}
	}
	
	private static boolean validateIntroducedDate(String introducedDate) throws ParseError {
		return validateDate(introducedDate);
	}
	
	private static boolean validateDiscontinuedDate(String discontinuedDate) throws ParseError {
		return validateDate(discontinuedDate);
	}

	private static boolean validateDate(String date) throws ParseError {
		if(!date.isEmpty()) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            dateFormat.parse(date);
	            return true;
	        } catch (ParseException e) {
	            throw new ParseError();
	        }
		}
		return false;
	}
	
	private static boolean validateDiscontinuedDateAfterIntroducedDate(String introducedDate, String discontinuedDate) throws ParseError, CustomDateException {
		if(validateDiscontinuedDate(discontinuedDate) && validateIntroducedDate(introducedDate)) {
			Date discontinuedDateDate;
			Date introducedDateDate;
			try {
				discontinuedDateDate = Utilitaire.stringToDateWebUI(discontinuedDate);
				introducedDateDate = Utilitaire.stringToDateWebUI(introducedDate);
			} catch (ParseException e) {
				return false;
			}
			
			if(discontinuedDateDate.getTime() >= introducedDateDate.getTime()) {
				return true;
			}
			else {
				throw new CustomDateException();
			}
		}
		return false;
	}
	
}

