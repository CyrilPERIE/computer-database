package main;

import java.sql.SQLException;
import java.text.ParseException;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args) throws ParseException, SQLException {
		
		View view = new View();
		ControllerCompany controllerCompany = new ControllerCompany(view);
		ControllerComputer controllerComputer = new ControllerComputer(view);
		view.setControllerCompany(controllerCompany);
		view.setControllerComputer(controllerComputer);
		view.client();
						
	}

}
