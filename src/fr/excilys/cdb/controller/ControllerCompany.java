package fr.excilys.cdb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DAOCompany;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;

public class ControllerCompany {

	protected static DAOCompany queriesCompany;

	public ControllerCompany() {
		queriesCompany = DAOCompany.getQueriesCompanyInstance();
	}

	/*
	 * ------------------------------------------ | REQUEST FUNCTIONS |
	 * ------------------------------------------
	 */

	public void listCompanies() {

		ResultSet resultSet = queriesCompany.listCompanies();
		try {
			while (resultSet.next()) {
				Company company = new Company.CompanyBuilder().computerId(resultSet.getInt("company.id"))
						.computerName(resultSet.getString("company.name")).build();
				System.out.println(company.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void listCompaniesPageable(int offset, int limit) {
		ResultSet resultSet = queriesCompany.listComputersPageable(offset, limit);
		try {
			while (resultSet.next()) {
				Company company = resultSetToCompanyObject(resultSet);
				System.out.println(company.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private Company resultSetToCompanyObject(ResultSet resultSet) {
		Company company = null;
		try {
			company = new Company.CompanyBuilder().computerId(resultSet.getInt("id"))
					.computerName(resultSet.getString("name")).build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}
