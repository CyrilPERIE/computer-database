package fr.excilys.cdb.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.LoggerInstance;

public class View {
	
	final private String LIST_COMPUTERS = "1";
	final private String LIST_COMPANIES = "2";
	final private String SHOW_SPECIFIC_COMPUTER = "3";
	final private String CERATE_COMPUTER = "4";
	final private String UPDATE_COMPUTER = "5";
	final private String DELETE_COMPUTER = "6";
	
	final private String CASE_PREVIOUS = "1";
	final private String CASE_NEXT = "2";
	
	public final static int UPDATE_MANUFACTURER_NAME = 1;
	public final static int UPDATE_COMPUTER_NAME = 2;
	public final static int UPDATE_INTRODUCED_DATE = 3;
	public final static int UPDATE_DISCONTINUED_DATE = 4;
	
	
	private Scanner scanner = new Scanner(System.in);
	private ControllerCompany controllerCompany;
	private ControllerComputer controllerComputer;
	
	LoggerInstance loggerInstance = LoggerInstance.getLoggerInstance();
	
	public View() {
		
	}

	public void client() throws ParseException{
		String choice;
		boolean continueBoolean = true;
		
		while(continueBoolean) {
			System.out.println("\n What do you want to do ?"
					+ "\n " + LIST_COMPUTERS + " : List computers"
					+ "\n " + LIST_COMPANIES + " : List companies"
					+ "\n " + SHOW_SPECIFIC_COMPUTER + " : Show computer details"
					+ "\n " + CERATE_COMPUTER + " : Create a computer"
					+ "\n " + UPDATE_COMPUTER + " : Update a computer"
					+ "\n " + DELETE_COMPUTER + " : Delete a computer"
					+ "\n Other : Exit");
			choice = scanner.next();
			continueBoolean = getChoiceFunction(choice);			
			
		}
		
	}
	
	public boolean getChoiceFunction(String choice) {
		switch(choice) {
			case LIST_COMPUTERS: try {
				listComputers();
			} catch (SQLException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.emptyBase.getMessage());
				 
			}finally {return true;}
			case LIST_COMPANIES: try {
				listCompanies();
			} catch (SQLException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.emptyBase.getMessage());
			}finally {return true;}
			case SHOW_SPECIFIC_COMPUTER: try {
				controllerComputer.showComputerDetails();
			} catch (SQLException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.wrongIDFormat.getMessage());
			}finally {return true;}
			case CERATE_COMPUTER: try {
				controllerComputer.createComputer();
			} catch (ParseException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.wrongDateFormat.getMessage());
			}finally {return true;}
			case UPDATE_COMPUTER: try {
				controllerComputer.updateComputer();
			} catch (ParseException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.wrongDateFormat.getMessage());
			} catch (SQLException e) {
				loggerInstance.expectLoop(LoggerInstance.MessageForScanner.nonExistentID.getMessage());
			}finally {return true;}
			case DELETE_COMPUTER: controllerComputer.deleteComputer(); return true;
			default: System.out.println("bye");return false;
		
		}
	}
	
	public void listComputers() throws SQLException {
		boolean exit = true;
		Pageable pageable = new Pageable();
		scanner.nextLine();
		do {
			controllerComputer.listComputersPageable(pageable);
			exit = pageableHandler(pageable);
		}while(exit);
	}
	
	public void listCompanies() throws SQLException {
		boolean exit = true;
		Pageable pageable = new Pageable();
		scanner.nextLine();
		do {
			controllerCompany.listCompaniesPageable(pageable.getOffset(),pageable.getLimit());
			exit = pageableHandler(pageable);
		}while(exit);
	}

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
			 loggerInstance.expectLoop(LoggerInstance.MessageForScanner.wrongIDFormat.getMessage());
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
