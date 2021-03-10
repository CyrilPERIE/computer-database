package fr.excilys.cdb.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.exception.LoggerInstance;
import fr.excilys.cdb.model.Company;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class View {
	
	private final String LIST_COMPUTERS = "1";
	private final String LIST_COMPANIES = "2";
	private final String SHOW_SPECIFIC_COMPUTER = "3";
	private final String CREATE_COMPUTER = "4";
	private final String UPDATE_COMPUTER = "5";
	private final String DELETE_COMPUTER = "6";
	
	private final String CASE_PREVIOUS = "1";
	private final String CASE_NEXT = "2";
	
	public static final int UPDATE_MANUFACTURER_NAME = 1;
	public static final int UPDATE_COMPUTER_NAME = 2;
	public static final int UPDATE_INTRODUCED_DATE = 3;
	public static final int UPDATE_DISCONTINUED_DATE = 4;
	
	
	private Scanner scanner = new Scanner(System.in);
	@Autowired
	private ControllerCompany controllerCompany;
	@Autowired
	private ControllerComputer controllerComputer;
	Pageable pageable = new Pageable();

	/*
	 * ------------------------------------------ 
	 * |            User Interaction            |
	 * ------------------------------------------
	 */	
	
	public void client() {
		String choice;
		boolean continueBoolean = true;
		
		while(continueBoolean) {
			System.out.println("\n What do you want to do ?"
					+ "\n " + LIST_COMPUTERS + " : List computers"
					+ "\n " + LIST_COMPANIES + " : List companies"
					+ "\n " + SHOW_SPECIFIC_COMPUTER + " : Show computer details"
					+ "\n " + CREATE_COMPUTER + " : Create a computer"
					+ "\n " + UPDATE_COMPUTER + " : Update a computer"
					+ "\n " + DELETE_COMPUTER + " : Delete a computer"
					+ "\n Other : Exit");
			choice = scanner.next();
			continueBoolean = getChoiceFunction(choice);			
			
		}
		
	}
	
	@SuppressWarnings("finally")
	public boolean getChoiceFunction(String choice) {
		boolean result = true;
		switch(choice) {
			case LIST_COMPUTERS: try {
				listComputers();
			} catch (SQLException e) {
				LoggerInstance.expectLoop(LoggerInstance.Messages.emptyBase.getMessage());
				 
			}finally {break;}
			case LIST_COMPANIES: try {
				listCompanies();
			} catch (SQLException e) {
				LoggerInstance.expectLoop(LoggerInstance.Messages.emptyBase.getMessage());
			}finally {break;}
			case SHOW_SPECIFIC_COMPUTER: try {
				controllerComputer.showComputerDetails();
			} catch (SQLException e) {
				LoggerInstance.expectLoop(LoggerInstance.Messages.wrongIDFormat.getMessage());
				e.printStackTrace();
			}finally {break;}
//			case CREATE_COMPUTER: try {
//				controllerComputer.createComputer();
//			} catch (ParseException e) {
//				loggerInstance.expectLoop(LoggerInstance.Messages.wrongDateFormat.getMessage());
//			}finally {break;}
			case UPDATE_COMPUTER: try {
				controllerComputer.updateComputer();
			} catch (SQLException e) {
				LoggerInstance.expectLoop(LoggerInstance.Messages.nonExistentID.getMessage());
			}finally {break;}
			case DELETE_COMPUTER: try {
				controllerComputer.deleteComputer();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (CustomSQLException e) {
				e.printStackTrace();
			} break;
			default: System.out.println("bye");result =  false;
		
		}
		return result;
	}

	/*
	 * ------------------------------------------ 
	 * |         From View to Controller        |
	 * ------------------------------------------
	 */
	
	public void listComputers() throws SQLException {
		boolean exit = true;
		Pageable pageable = new Pageable();
		scanner.nextLine();
		do {
			try {
				controllerComputer.listComputersPageable(pageable);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (CustomSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exit = pageableHandler(pageable);
		}while(exit);
	}
	
	public void listCompanies() throws SQLException {
		boolean exit = true;
		Pageable pageable = new Pageable();
		scanner.nextLine();
		do {
			try {
				controllerCompany.listCompaniesPageable(pageable);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CustomSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exit = pageableHandler(pageable);
		}while(exit);
	}

	/*
	 * ------------------------------------------ 
	 * |         From Controller to View        |
	 * ------------------------------------------
	 */
	
	private boolean pageableHandler(Pageable pageable) {
		boolean exit;
		navigatePreviousNext();
		String choice = scanner.nextLine();
		switch(choice) {
		case CASE_PREVIOUS: pageable.previous();exit = true;break;
		case CASE_NEXT: pageable.next();exit = true;break;
		default: exit = false;
		}
		return exit;
	}
	
	public void printCompany(Company company) {
		System.out.println(company.toString());
		
	}

	public void enterInsertComputer() {
		System.out.println("--- Insert a Computer ---");
		
	}

	public String getComputerName() {
		System.out.println("Name of the computer (press ENTER key if you don't have the name) : ");
		scanner.nextLine();
		return scanner.nextLine();
	}

	public String getCompanyName() {
		System.out.println("Name of the company (press ENTER key if you don't have the name) : ");
		return scanner.nextLine();
	}
	
	public String getIntroducedDate() {
		System.out.println("Introduced date of the computer (press ENTER key if you don't have the date) dd/mm/yyyy : ");
		return scanner.nextLine();
	}

	public String getDiscontinuedDate() {
		System.out.println("Discontinued date of the computer (press ENTER key if you don't have the date) dd/mm/yyyy : ");
		return scanner.nextLine();
	}
	
	private void navigatePreviousNext() {
		System.out.println("<-- Previous(" + CASE_PREVIOUS + ")       Exit(Other)       Next(" + CASE_NEXT + ") -->");
	}

	public ControllerCompany getControllerCompany() {
		return controllerCompany;
	}

	public void setControllerCompany(ControllerCompany controllerCompany) {
		this.controllerCompany = controllerCompany;
	}

	public ControllerComputer getControllerComputer() {
		return controllerComputer;
	}

	public void setControllerComputer(ControllerComputer controllerComputer) {
		this.controllerComputer = controllerComputer;
	}

	public void enterShowComputerDetails() {
		System.out.println("--- Show Computer Details ---");
		
	}

	public int getComputerId() {
		System.out.println("Which computer id ?");
		return scannerNextInt();
	}

	private int scannerNextInt() {
		try{
			return scanner.nextInt();
		}
		catch(Exception e) {
			LoggerInstance.expectLoop(LoggerInstance.Messages.wrongIDFormat.getMessage());
			 return 0;
		}
	}

	public void enterUpdaterComputer() {
		System.out.println("--- Update Computer ---");
		
	}

	public int getUpdateChoice() {
		System.out.println("What do you want to update ? (" + UPDATE_MANUFACTURER_NAME + "," + UPDATE_COMPUTER_NAME + "," + UPDATE_INTRODUCED_DATE + "," + UPDATE_DISCONTINUED_DATE + ")");
		return scannerNextInt();
	}

	public String askNewManufacturer() {
		System.out.println("new manufacturer name : ");
		scanner.nextLine();
		return scanner.nextLine();
	}

	public String askNewComputerName() {
		System.out.println("new computer name : ");
		scanner.nextLine();
		return scanner.nextLine();
	}

	public String askNewIntroducedDate() {
		System.out.println("new introduced date (dd/mm/yyyy) : ");
		scanner.nextLine();
		return scanner.nextLine();
	}

	public String askNewDiscontinuedDate() {
		System.out.println("new discontinued date (dd/mm/yyyy) : ");
		scanner.nextLine();
		return scanner.nextLine();
	}

	public void enterDeleteComputer() {
		System.out.println("--- Delete Computer ---");		
	}

	public int askComputerId() {
		System.out.println("Which computer id ?");
		return scannerNextInt();
	}

}
