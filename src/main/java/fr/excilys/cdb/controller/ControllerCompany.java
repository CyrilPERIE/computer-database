package fr.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.ServiceCompany;
import fr.excilys.cdb.view.View;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ControllerCompany {
	
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private static View view;

	/*
	 * ------------------------------------------ 
	 * |            REQUEST FUNCTIONS           |
	 * ------------------------------------------
	 */

	public void listCompaniesPageable(Pageable pageable) throws ClassNotFoundException, CustomSQLException, SQLException {
		List<Company> companies = serviceCompany.listCompaniesPageable(pageable);
		for(Company company : companies) {
			view.printCompany(company);
		}

	}

	public int getIdCompany(String companyName) throws ClassNotFoundException, SQLException, CustomSQLException {
		return serviceCompany.getIDCompany(companyName);
	}
	
}
