package fr.excilys.cdb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import fr.excilys.cdb.database.DAOComputer;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.LoggerInstance;
import fr.excilys.cdb.view.View;

public class ControllerComputer {

	protected static DAOComputer daoComputer;
	private View view;
	LoggerInstance loggerInstance = LoggerInstance.getLoggerInstance();
	
	public ControllerComputer(View view) {
		daoComputer = DAOComputer.getQueriesComputerInstance();
		this.view = view;
	}

	/*
	 * ------------------------------------------ 
	 * |             REQUEST FUNCTIONS          |
	 * ------------------------------------------
	 */

	public void listComputers() throws SQLException {
		ResultSet resultSet = daoComputer.listComputers();
		try {
			while (resultSet.next()) {
				Computer computer = resultSetToComputerObject(resultSet);
				computerToString(computer);
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public void listComputersPageable(Pageable pageable) throws SQLException {
		ResultSet resultSet = daoComputer.listComputersPageable(pageable);
		try {
			while (resultSet.next()) {
				Computer computer = resultSetToComputerObject(resultSet);
				computerToString(computer);
			}
		} catch (SQLException e) {
			throw e;
		}

	}

	/*
	 * ------------------------------------------ 
	 * |          MODIFICATIONS FUNCTIONS       |
	 * ------------------------------------------
	 */

	public void createComputer() throws ParseException {
		view.enterInsertComputer();
		String computerName = view.getComputerName();
		String companyName = view.getCompanyName();
		String introducedDate = view.getIntroducedDate();
		String discontinuedDate = view.getDiscontinuedDate();

		int companyId = ControllerCompany.queriesCompany.getIdCompany(companyName);

		daoComputer.createComputer(companyId, computerName, Utilitaire.stringToDate(introducedDate),
				Utilitaire.stringToDate(discontinuedDate));
	}

	public void showComputerDetails() throws SQLException {
		view.enterShowComputerDetails();
		int computerId = view.getComputerId();
		ResultSet resultSet = daoComputer.showComputerDetails(computerId);
		try {
			resultSet.next();
		} catch (SQLException e) {
			throw e;
		}
		Computer computer = resultSetToComputerObject(resultSet);
		computerToString(computer);
	}

	public void updateComputer() throws ParseException, SQLException {
		view.enterUpdaterComputer();
		int computerId = view.getComputerId();
		ResultSet resultSet = daoComputer.showComputerDetails(computerId);
		try {
			resultSet.next();
		} catch (SQLException e) {
			throw e;
		}
		Computer computer = resultSetToComputerObject(resultSet);
		computer.listElements();
		int updateChoice = view.getUpdateChoice();
		updateChoice(updateChoice, computerId);

	}

	private void updateChoice(int updateChoice, int computerId) throws ParseException {
		switch (updateChoice) {
		case View.UPDATE_MANUFACTURER_NAME:
			String manufacturer = view.askNewManufacturer();
			int companyId = ControllerCompany.queriesCompany.getIdCompany(manufacturer);
			daoComputer.updateManufacturer(companyId, computerId);
			break;
		case View.UPDATE_COMPUTER_NAME:
			String newCompanyName = view.askNewComputerName();
			daoComputer.updateComputerName(newCompanyName, computerId);
			break;
		case View.UPDATE_INTRODUCED_DATE:
			String newIntroducedDate = view.askNewIntroducedDate();
			daoComputer.updateIntroducedDate(Utilitaire.stringToDate(newIntroducedDate), computerId);
			break;
		case View.UPDATE_DISCONTINUED_DATE:
			String newDiscontinuedDate = view.askNewDiscontinuedDate();
			daoComputer.updateDiscontinuedDate(Utilitaire.stringToDate(newDiscontinuedDate), computerId);
		}

	}

	public void deleteComputer() {
		view.enterDeleteComputer();;
		int computerId = view.askComputerId();
		daoComputer.deleteComputer(computerId);

	}

	/*
	 * ------------------------------------------ 
	 * |              DISPLAY FUNCTIONS         |
	 * ------------------------------------------
	 */

	private Computer resultSetToComputerObject(ResultSet resultSet) throws SQLException {
		Computer computer = null;
		try {
			Company company = new Company.CompanyBuilder().computerId(resultSet.getInt("computer.id"))
					.computerName(resultSet.getString("company.name")).build();

			computer = new Computer.ComputerBuilder().computerId(resultSet.getInt("computer.id"))
					.computerManufacturer(company).computerName(resultSet.getString("computer.name"))
					.computerIntroducedDate(resultSet.getDate("computer.introduced"))
					.computerDiscontinuedDate(resultSet.getDate("computer.discontinued")).build();

		} catch (SQLException e) {
			throw e;
		}
		return computer;
	}

	private void computerToString(Computer computer) {
		System.out.println(computer.toString());
	}

}
