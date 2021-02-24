package fr.excilys.cdb.services;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.cdb.database.DAOCompany;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Company;

public class ServiceCompany {
	
	private static ServiceCompany serviceCompany;
	private DAOCompany daoCompany;

	private ServiceCompany() {
		this.daoCompany = DAOCompany.getDAOCompanyInstance();
	}

	/*
	 * ------------------------------------------ 
	 * | 				SINGLETON				|
	 * ------------------------------------------
	 */

	public static ServiceCompany getServiceCompanyInstance() {
		if (serviceCompany == null) {
			serviceCompany = new ServiceCompany();
		}
		return serviceCompany;
	}

	public List<Company> listCompanies() throws SQLException {
		return daoCompany.listCompanies();
	}

	public List<Company> listCompaniesPageable(Pageable pageable) throws SQLException {
		return daoCompany.listCompaniesPageable(pageable);
	}

	public int getIDCompany(String companyName) throws SQLException {
		return daoCompany.getIdCompany(companyName);
	}
}
