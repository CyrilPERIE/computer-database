package fr.excilys.cdb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilitaire {
	
	public static java.sql.Date stringToDate(String date) throws ParseException {
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	     Date parsed = format.parse(date);
	     java.sql.Date sql = new java.sql.Date(parsed.getTime());
	     return sql;
        
	}

	public static java.sql.Date stringToDateWebUI(String date) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     Date parsed = null;
		try {
			parsed = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     java.sql.Date sql = new java.sql.Date(parsed.getTime());
	     return sql;
	}
	
}
