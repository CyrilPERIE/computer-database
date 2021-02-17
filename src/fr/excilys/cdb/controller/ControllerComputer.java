package fr.excilys.cdb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import database.DAOComputer;
import database.Pageable;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;

public class ControllerComputer {

	protected static DAOComputer daoComputer;
	Scanner scanner = new Scanner(System.in);

	public ControllerComputer() {
		daoComputer = DAOComputer.getQueriesComputerInstance();
	}

	/*
	 * ------------------------------------------ 
	 * |             REQUEST FUNCTIONS          |
	 * ------------------------------------------
	 */

	public void listComputers() {
		ResultSet resultSet = daoComputer.listComputers();
		try {
			while (resultSet.next()) {
				Computer computer = resultSetToComputerObject(resultSet);
				computerToString(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listComputersPageable(int offset, int limit) {
		ResultSet resultSet = daoComputer.listComputersPageable(offset, limit);
		try {
			while (resultSet.next()) {
				Computer computer = resultSetToComputerObject(resultSet);
				computerToString(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * ------------------------------------------ 
	 * |          MODIFICATIONS FUNCTIONS       |
	 * ------------------------------------------
	 */

	public void createComputer() throws ParseException {
		System.out.println("--- Insert a Computer ---");
		System.out.println("Name of the computer (press ENTER key if you don't have the name) : ");
		String computerName = scanner.nextLine();
		System.out.println("Name of the company (press ENTER key if you don't have the name) : ");
		String companyName = scanner.nextLine();
		System.out
				.println("Introduced date of the computer (press ENTER key if you don't have the date) dd/mm/yyyy : ");
		String introducedDate = scanner.nextLine();
		System.out.println(
				"Discontinued date of the computer (press ENTER key if you don't have the date) dd/mm/yyyy : ");
		String discontinuedDate = scanner.nextLine();

		int companyId = ControllerCompany.queriesCompany.getIdCompany(companyName);

		daoComputer.createComputer(companyId, computerName, Utilitaire.stringToDate(introducedDate),
				Utilitaire.stringToDate(discontinuedDate));
	}

	public void showComputerDetails() {
		System.out.println("--- Show Computer Details ---");
		System.out.println("Which computer id ?");
		int computerId = scanner.nextInt();
		ResultSet resultSet = daoComputer.showComputerDetails(computerId);
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Computer computer = resultSetToComputerObject(resultSet);
		computerToString(computer);
	}

	public void updateComputer() throws ParseException {
		System.out.println("--- Update Computer ---");
		System.out.println("Which computer id ?");
		int computerId = scanner.nextInt();
		ResultSet resultSet = daoComputer.showComputerDetails(computerId);
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Computer computer = resultSetToComputerObject(resultSet);
		computer.listElements();
		System.out.println("What do you want to update ? (1,2,3,4)");
		updateChoice(scanner.nextInt(), computerId);

	}

	private void updateChoice(int updateChoice, int computerId) throws ParseException {
		switch (updateChoice) {
		case 1:
			System.out.println("new manufacturer name : ");
			String manufacturer = scanner.next();
			int companyId = ControllerCompany.queriesCompany.getIdCompany(manufacturer);
			daoComputer.updateManufacturer(companyId, computerId);
			break;
		case 2:
			System.out.println("new computer name : ");
			daoComputer.updateComputerName(scanner.next(), computerId);
			break;
		case 3:
			System.out.println("new introduced date (dd/mm/yyyy) : ");
			daoComputer.updateIntroducedDate(Utilitaire.stringToDate(scanner.next()), computerId);
			break;
		case 4:
			System.out.println("new discontinued date (dd/mm/yyyy) : ");
			daoComputer.updateDiscontinuedDate(Utilitaire.stringToDate(scanner.next()), computerId);
		}

	}

	public void deleteComputer() {
		System.out.println("--- Delete Computer ---");
		System.out.println("Which computer id ?");
		int computerId = scanner.nextInt();
		daoComputer.deleteComputer(computerId);

	}

	/*
	 * ------------------------------------------ 
	 * |              DISPLAY FUNCTIONS         |
	 * ------------------------------------------
	 */

	private Computer resultSetToComputerObject(ResultSet resultSet) {
		Computer computer = null;
		try {
			Company company = new Company.CompanyBuilder().computerId(resultSet.getInt("computer.id"))
					.computerName(resultSet.getString("company.name")).build();

			computer = new Computer.ComputerBuilder().computerId(resultSet.getInt("computer.id"))
					.computerManufacturer(company).computerName(resultSet.getString("computer.name"))
					.computerIntroducedDate(resultSet.getDate("computer.introduced"))
					.computerDiscontinuedDate(resultSet.getDate("computer.discontinued")).build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	private void computerToString(Computer computer) {
		System.out.println(computer.toString());
	}

}
