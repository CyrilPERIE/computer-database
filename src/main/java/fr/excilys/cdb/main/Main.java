package fr.excilys.cdb.main;

import java.sql.SQLException;
import java.text.ParseException;

import fr.excilys.cdb.controller.ControllerCompany;
import fr.excilys.cdb.controller.ControllerComputer;
import fr.excilys.cdb.database.DAOCompany;
import fr.excilys.cdb.database.DAOComputer;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.view.View;

public class Main {
	
	public static void main(String[] args) throws ParseException, SQLException {
		
//		View view = View.getViewInstance();
//		ControllerCompany.setView();
//		ControllerComputer.setView();
//		view.client();
		
		DAOCompany daoCompany = DAOCompany.getInstance();
		Company company = new Company.CompanyBuilder().companyId(1).build();
		company.setId(1);
		try {
			daoCompany.deleteCompany(company);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
