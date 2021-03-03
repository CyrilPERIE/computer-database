package fr.excilys.cdb.services;

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
		this.daoComputer = DAOComputer.getInstance();
	}

	/*
	 * ------------------------------------------ 
	 * | 				SINGLETON				|
	 * ------------------------------------------
	 */

	public static ServiceComputer getInstance() {
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

	public List<Computer> listComputersPageable(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		return daoComputer.listComputersPageable(pageable);
	}

	public List<Computer> showComputerDetails(int computerId) throws SQLException, ClassNotFoundException, CustomSQLException {
		return daoComputer.showComputerDetails(computerId);
	}

	public void createComputer(Computer computer) throws CustomSQLException, ClassNotFoundException {
		daoComputer.createComputer(computer);
		
	}

	public void updateComputer(Computer computer, int computerId) throws ClassNotFoundException, CustomSQLException {
		daoComputer.updateComputer(computer, computerId);
		
	}

	public void deleteComputer(int computerId) throws ClassNotFoundException, CustomSQLException {
		daoComputer.deleteComputer(computerId);
		
	}

	public int totalNumberComputer() throws ClassNotFoundException, CustomSQLException {
		return daoComputer.totalNumberComputer();
	}
	
	/*
	 * ------------------------------------------ 
	 * | 		From DAO to Controller			|
	 * ------------------------------------------
	 */
}
