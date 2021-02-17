package fr.excilys.cdb.view;

import java.text.ParseException;
import java.util.Scanner;

import database.Pageable;
import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;

public class View {

	private Scanner scanner = new Scanner(System.in);
	private ControllerCompany controllerCompany;
	private ControllerComputer controllerComputer;
	
	public View() {
		
	}

	public void client() throws ParseException{
		String choice;
		boolean continueBoolean = true;
		
		while(continueBoolean) {
			System.out.println("");
			System.out.println("What do you want to do ?");
			System.out.println("1 : List computers"); //OK
			System.out.println("2 : List companies"); //OK
			System.out.println("3 : Show computer details"); //OK
			System.out.println("4 : Create a computer"); 
			System.out.println("5 : Update a computer"); 
			System.out.println("6 : Delete a computer"); //OK
			System.out.println("Other : Exit");
			choice = scanner.next();
			continueBoolean = getChoiceFunction(choice);
			
			
		}
		
	}
	
	public boolean getChoiceFunction(String choice) throws ParseException{
		boolean result = true;
		switch(choice) {
			case "1": listComputers(); break;
			case "2": listCompanies(); break;
			case "3": controllerComputer.showComputerDetails(); break;
			case "4": controllerComputer.createComputer(); break;
			case "5": controllerComputer.updateComputer(); break;
			case "6": controllerComputer.deleteComputer(); break;
			default: result = false; System.out.println("bye");
		
		}
		return result;
	}
	
	public void listComputers() {
		boolean exit = true;
		Pageable pageable = new Pageable();
		do {
			controllerComputer.listComputersPageable(pageable.getOffset(),pageable.getLimit());
			System.out.println("<-- Previous(1)    Exit(Other)       Next(2) -->");
			String choice = scanner.next();
			switch(choice) {
			case "1": pageable.previous();break;
			case "2": pageable.next();break;
			default: exit = false;
			}
		}while(exit);
	}
	
	public void listCompanies() {
		boolean exit = true;
		Pageable pageable = new Pageable();
		do {
			controllerCompany.listCompaniesPageable(pageable.getOffset(),pageable.getLimit());
			System.out.println("<-- Previous(1)    Exit(Other)       Next(2) -->");
			String choice = scanner.next();
			switch(choice) {
			case "1": pageable.previous();break;
			case "2": pageable.next();break;
			default: exit = false;
			}
		}while(exit);
	}
	
	
	
	public void setControllers(ControllerCompany controllerCompany, ControllerComputer controllerComputer) {
		this.controllerCompany = controllerCompany;
		this.controllerComputer = controllerComputer;
		
	}

}
