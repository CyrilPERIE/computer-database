package fr.excilys.cdb.dto.validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.dto.EditComputerFormInput;
import fr.excilys.cdb.exception.CustomDateException;
import fr.excilys.cdb.exception.EmptyError;
import fr.excilys.cdb.exception.ParseError;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ValidatorEditComputer {
	
	/*
	 * ------------------------------------------ 
	 * | 				Test Fcs				|
	 * ------------------------------------------
	 */
	
	public static void validate(EditComputerFormInput editComputerFormInput) throws ParseError, EmptyError, CustomDateException {
		validateComputerName(editComputerFormInput.getComputerName());
		validateDiscontinuedDateAfterIntroducedDate(editComputerFormInput.getIntroducedDate(), editComputerFormInput.getDiscontinuedDate());
	}
	
	private static void validateComputerName(String computerName) throws EmptyError {
		if(computerName.isEmpty()) {
			throw new EmptyError();
		}
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
		System.out.println("retourne faux pour validate date");
		return false;
	}
	
	private static void validateDiscontinuedDateAfterIntroducedDate(String introducedDate, String discontinuedDate) throws ParseError, CustomDateException {
		if(validateDate(discontinuedDate) && validateDate(introducedDate)) {
			Date discontinuedDateDate;
			Date introducedDateDate;
			try {
				discontinuedDateDate = Utilitaire.stringToDateWebUI(discontinuedDate);
				introducedDateDate = Utilitaire.stringToDateWebUI(introducedDate);
				if(!(discontinuedDateDate.getTime() >= introducedDateDate.getTime())) throw new CustomDateException();
			} catch (ParseException e) {
				throw new ParseError();
			}
			
		}
		System.out.println("retourne faux pour after");
	}
	
}