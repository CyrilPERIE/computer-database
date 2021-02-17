package main;

import java.text.ParseException;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.view.View;

public class Main {

	public static void main(String[] args) throws ParseException {
		
		View view = new View();
		ControllerCompany controllerCompany = new ControllerCompany();
		ControllerComputer controllerComputer = new ControllerComputer();
		view.setControllers(controllerCompany,controllerComputer);
		view.client();
		
		

	}

}
