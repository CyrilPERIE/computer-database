package fr.excilys.cdb.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.LoggerInstance;
import fr.excilys.cdb.services.ServiceComputer;
import fr.excilys.cdb.view.View;

public class ControllerComputer {

	LoggerInstance loggerInstance = LoggerInstance.getLoggerInstance();
	private ServiceComputer serviceComputer;
	private static View view;
	private ControllerCompany controllerCompany;
	
	private static ControllerComputer controllerComputer;

	private ControllerComputer() {
		this.serviceComputer = ServiceComputer.getServiceComputerInstance();
		this.controllerCompany = ControllerCompany.getControllerCompanyInstance();
	}
	
	public static void setView() {
		view = View.getViewInstance();
	}
	/*
	 * ------------------------------------------ 
	 * |                SINGLETON               |
	 * ------------------------------------------
	 */

	public static ControllerComputer getControllerComputerInstance() {
		if (controllerComputer == null) {
			controllerComputer = new ControllerComputer();
		}
		return controllerComputer;
	}

	/*
	 * ------------------------------------------ 
	 * |             REQUEST FUNCTIONS          |
	 * ------------------------------------------
	 */

	public void listComputersPageable(Pageable pageable) throws SQLException {
		List<Computer> computers = serviceComputer.listComputersPageable(pageable);
		for(Computer computer : computers) {
			computerToString(computer);
			}

	}

	public void showComputerDetails() throws SQLException {
		view.enterShowComputerDetails();
		int computerId = view.getComputerId();
		List<Computer> computers = serviceComputer.showComputerDetails(computerId);
		for(Computer computer : computers) {
			computerToString(computer);
			}
	}

	/*
	 * ------------------------------------------ 
	 * |          MODIFICATIONS FUNCTIONS       |
	 * ------------------------------------------
	 */

	public void createComputer() throws ParseException, SQLException {
		System.out.println("jentre dans create computer controller");
		view.enterInsertComputer();
		System.out.println("je sors de enterInsertComputer");
		String computerName = view.getComputerName();
		String companyName = view.getCompanyName();
		String introducedDate = view.getIntroducedDate();
		String discontinuedDate = view.getDiscontinuedDate();
		System.out.println("fin des saisies");
		int companyId = controllerCompany.getIdCompany(companyName);

		serviceComputer.createComputer(companyId, computerName, Utilitaire.stringToDate(introducedDate),
				Utilitaire.stringToDate(discontinuedDate));
	}

	public void updateComputer() throws ParseException, SQLException {
		view.enterUpdaterComputer();
		int computerId = view.getComputerId();
		List<Computer> computers = serviceComputer.showComputerDetails(computerId);
		for(Computer computer : computers) {
			computer.listElements();
		}
		int updateChoice = view.getUpdateChoice();
		updateChoice(updateChoice, computerId);

	}

	private void updateChoice(int updateChoice, int computerId) throws ParseException, SQLException {
		switch (updateChoice) {
		case View.UPDATE_MANUFACTURER_NAME:
			String manufacturer = view.askNewManufacturer();
			int companyId = controllerCompany.getIdCompany(manufacturer);
			serviceComputer.updateManufacturer(companyId, computerId);
			break;
		case View.UPDATE_COMPUTER_NAME:
			String newCompanyName = view.askNewComputerName();
			serviceComputer.updateComputerName(newCompanyName, computerId);
			break;
		case View.UPDATE_INTRODUCED_DATE:
			String newIntroducedDate = view.askNewIntroducedDate();
			serviceComputer.updateIntroducedDate(Utilitaire.stringToDate(newIntroducedDate), computerId);
			break;
		case View.UPDATE_DISCONTINUED_DATE:
			String newDiscontinuedDate = view.askNewDiscontinuedDate();
			serviceComputer.updateDiscontinuedDate(Utilitaire.stringToDate(newDiscontinuedDate), computerId);
		}

	}

	public void deleteComputer() {
		view.enterDeleteComputer();;
		int computerId = view.askComputerId();
		serviceComputer.deleteComputer(computerId);

	}

	/*
	 * ------------------------------------------ 
	 * |              DISPLAY FUNCTIONS         |
	 * ------------------------------------------
	 */

	private void computerToString(Computer computer) {
		System.out.println(computer.toString());
	}

	public int totalNumberComputer() {
		return serviceComputer.totalNumberComputer();
	}

}
