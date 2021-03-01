package fr.excilys.cdb.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import fr.excilys.cdb.database.DAOComputer;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Computer;

public class ServiceComputer {

	private static ServiceComputer serviceComputer;
	private DAOComputer daoComputer;

	private ServiceComputer() {
		this.daoComputer = DAOComputer.getDAOComputerInstance();
	}

	/*
	 * ------------------------------------------ 
	 * | 				SINGLETON				|
	 * ------------------------------------------
	 */

	public static ServiceComputer getServiceComputerInstance() {
		if (serviceComputer == null) {
			serviceComputer = new ServiceComputer();
		}
		return serviceComputer;
	}


	/*
	 * ------------------------------------------ 
	 * | 		From Controller to DAO			|
	 * ------------------------------------------
	 */

	public List<Computer> listComputersPageable(Pageable pageable) throws SQLException {
		return daoComputer.listComputersPageable(pageable);
	}

	public List<Computer> showComputerDetails(int computerId) throws SQLException {
		return daoComputer.showComputerDetails(computerId);
	}

	public void createComputer(Computer computer) throws CustomSQLException {
		daoComputer.createComputer(computer);
		
	}

	public void updateManufacturer(int companyId, int computerId) {
		daoComputer.updateManufacturer(companyId, computerId);
		
	}

	public void updateComputerName(String newCompanyName, int computerId) {
		daoComputer.updateComputerName(newCompanyName, computerId);
		
	}

	public void updateIntroducedDate(Date stringToDate, int computerId) {
		daoComputer.updateIntroducedDate(stringToDate, computerId);
		
	}

	public void updateDiscontinuedDate(Date stringToDate, int computerId) {
		daoComputer.updateDiscontinuedDate(stringToDate, computerId);
		
	}

	public void deleteComputer(int computerId) {
		daoComputer.deleteComputer(computerId);
		
	}

	public int totalNumberComputer() {
		return daoComputer.totalNumberComputer();
	}
	
	/*
	 * ------------------------------------------ 
	 * | 		From DAO to Controller			|
	 * ------------------------------------------
	 */
}
