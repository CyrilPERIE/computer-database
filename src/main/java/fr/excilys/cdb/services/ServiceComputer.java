package fr.excilys.cdb.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.database.DAOComputer;
import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Computer;

@Component
public class ServiceComputer {
	
	@Autowired
	private DAOComputer daoComputer;


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

	public int totalNumberComputer(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		return daoComputer.totalNumberComputer(pageable);
	}

	public List<Computer> selectComputersLikePageableOrderBy(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		return daoComputer.selectComputersLikePageableOrderBy(pageable);
	}
	
	/*
	 * ------------------------------------------ 
	 * | 		From DAO to Controller			|
	 * ------------------------------------------
	 */
}
