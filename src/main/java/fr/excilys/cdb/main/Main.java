package fr.excilys.cdb.main;

import java.sql.SQLException;
import java.text.ParseException;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args) throws ParseException, SQLException {
		
		View view = View.getViewInstance();
		ControllerCompany.setView();
		ControllerComputer.setView();
		view.client();
		
						
	}

}
