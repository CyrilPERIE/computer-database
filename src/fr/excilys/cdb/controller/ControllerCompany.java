package fr.excilys.cdb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DAOCompany;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.view.View;

public class ControllerCompany {

	protected static DAOCompany queriesCompany;
	private View view;

	public ControllerCompany(View view) {
		queriesCompany = DAOCompany.getQueriesCompanyInstance();
		this.view = view;
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
				view.printCompany(company);
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
				view.printCompany(company);
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
