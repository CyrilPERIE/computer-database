package fr.excilys.cdb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Utilitaire {
	
	public static java.sql.Date stringToDate(String date) throws ParseException {
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	     Date parsed = format.parse(date);
	     java.sql.Date sql = new java.sql.Date(parsed.getTime());
	     return sql;
        
	}

	public static java.sql.Date stringToDateWebUI(String date) throws ParseException {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     Date parsed = format.parse(date);
	     java.sql.Date sql = new java.sql.Date(parsed.getTime());
	     return sql;
	}

	public static java.sql.Date LocalDateToDate(LocalDate localDate) {
		java.sql.Date date = java.sql.Date.valueOf(localDate);
		return date;
	}
	
}
