package fr.excilys.cdb.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.database.DAOCompany;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Company;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceCompany {
	
	@Autowired
	private DAOCompany daoCompany;

	public List<Company> listCompanies() throws CustomSQLException, ClassNotFoundException {
		return daoCompany.listCompanies();
	}
	
	public List<Company> listCompaniesPageable(Pageable pageable) throws SQLException, ClassNotFoundException, CustomSQLException {
		return daoCompany.listCompaniesPageable(pageable);
	}

	public int getIDCompany(String companyName) throws SQLException, CustomSQLException, ClassNotFoundException {
		return daoCompany.getIdCompany(companyName);
	}
}
