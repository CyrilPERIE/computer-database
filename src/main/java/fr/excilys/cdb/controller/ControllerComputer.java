package fr.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.exception.LoggerInstance;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceComputer;
import fr.excilys.cdb.view.View;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ControllerComputer {
	
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private static View view;

	/*
	 * ------------------------------------------ 
	 * |             REQUEST FUNCTIONS          |
	 * ------------------------------------------
	 */

	public void listComputersPageable(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		List<Computer> computers = serviceComputer.listComputersPageable(pageable);
		for(Computer computer : computers) {
			computerToString(computer);
			}

	}

	public void showComputerDetails() throws ClassNotFoundException, SQLException, CustomSQLException {
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

	public void updateComputer() throws ClassNotFoundException, SQLException, CustomSQLException {
		view.enterUpdaterComputer();
		int computerId = view.getComputerId();
		List<Computer> computers = serviceComputer.showComputerDetails(computerId);
		for(Computer computer : computers) {
			computer.listElements();
		}

	}

	public void deleteComputer() throws ClassNotFoundException, CustomSQLException {
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

	public int totalNumberComputer(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		return serviceComputer.totalNumberComputer(pageable);
	}

}
