package fr.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.ServiceCompany;
import fr.excilys.cdb.view.View;

public class ControllerCompany {
	
	private ServiceCompany serviceCompany;
	private static ControllerCompany controllerCompany;
	private static View view;

	private ControllerCompany() {
		this.serviceCompany = ServiceCompany.getServiceCompanyInstance();
	}
	
	public static void setView() {
		view = View.getViewInstance();
	}
	/*
	 * ------------------------------------------ 
	 * | 				SINGLETON				|
	 * ------------------------------------------
	 */

	public static ControllerCompany getControllerCompanyInstance() {
		if (controllerCompany == null) {
			controllerCompany = new ControllerCompany();
		}
		return controllerCompany;
	}

	/*
	 * ------------------------------------------ 
	 * |            REQUEST FUNCTIONS           |
	 * ------------------------------------------
	 */

	public void listCompaniesPageable(Pageable pageable) throws SQLException {
		List<Company> companies = serviceCompany.listComputersPageable(pageable);
		for(Company company : companies) {
			view.printCompany(company);
		}

	}

	public int getIdCompany(String companyName) throws SQLException {
		return serviceCompany.getIDCompany(companyName);
	}
	
}
