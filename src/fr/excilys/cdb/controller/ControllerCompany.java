package fr.excilys.cdb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.cdb.database.DAOCompany;
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

	public void listCompanies() throws SQLException {

		ResultSet resultSet = queriesCompany.listCompanies();
		try {
			while (resultSet.next()) {
				Company company = new Company.CompanyBuilder().computerId(resultSet.getInt("company.id"))
						.computerName(resultSet.getString("company.name")).build();
				view.printCompany(company);
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public void listCompaniesPageable(int offset, int limit) throws SQLException {
		ResultSet resultSet = queriesCompany.listComputersPageable(offset, limit);
		try {
			while (resultSet.next()) {
				Company company = resultSetToCompanyObject(resultSet);
				view.printCompany(company);
			}
		} catch (SQLException e) {
			throw e;
		}

	}

	private Company resultSetToCompanyObject(ResultSet resultSet) throws SQLException {
		Company company = null;
		try {
			company = new Company.CompanyBuilder().computerId(resultSet.getInt("id"))
					.computerName(resultSet.getString("name")).build();

		} catch (SQLException e) {
			throw e;
		}
		return company;
	}
}
