package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Company.CompanyBuilder;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Computer.ComputerBuilder;

public class DAOComputer {
	
	private static DAOComputer daoComputer;
	private ConnectionHandler connectionHandler;
	
	private DAOComputer() {
		this.connectionHandler = ConnectionHandler.getConnectionInstance();
	}

	/*
	 * ------------------------------------------
	 * |               SINGLETON                |
	 * ------------------------------------------
	 */
	
	public static DAOComputer getDAOComputerInstance() {
		if(daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	/*
	 * ------------------------------------------
	 * |            DIVE IN DB FCs              |
	 * ------------------------------------------
	 */
	
	public ResultSet query(String query) {
		ResultSet resultSet = null;
		try(Connection connection = connectionHandler.openConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException exception) {
			
			exception.printStackTrace();
		}
		return resultSet;
	}
	
	public void execute(String query) {
		Statement statement;
		try(Connection connection = connectionHandler.openConnection()) {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ------------------------------------------
	 * |       ResultSet Cleaning FCs           |
	 * ------------------------------------------
	 */
	
	public Computer resultSetToComputerObject(ResultSet resultSet) throws SQLException {
		Computer computer = null;
		try {
			Company company = new CompanyBuilder().computerId(resultSet.getInt("computer.id"))
					.computerName(resultSet.getString("company.name")).build();
	
			computer = new ComputerBuilder().computerId(resultSet.getInt("computer.id"))
					.computerManufacturer(company).computerName(resultSet.getString("computer.name"))
					.computerIntroducedDate(resultSet.getDate("computer.introduced"))
					.computerDiscontinuedDate(resultSet.getDate("computer.discontinued")).build();
	
		} catch (SQLException e) {
			throw e;
		}
		return computer;
	}
		
	/*
	 * ------------------------------------------
	 * |               Query FCs                |
	 * ------------------------------------------
	 */
	
	public List<Computer> listComputers() throws SQLException {
		String request = "SELECT *"
				+ " FROM computer"
				+ " JOIN company ON company.id = computer.company_id";
		ResultSet resultSet = query(request);
		List<Computer> computers = new ArrayList<Computer>();
		while(resultSet.next()) {
			computers.add(resultSetToComputerObject(resultSet));
		}
		return computers;
	}
	
	public List<Computer> listComputersPageable(Pageable pageable) throws SQLException {
		Connection connection = connectionHandler.openConnection();
		String request = "SELECT * FROM computer JOIN company ON company.id = computer.company_id limit ? offset ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			preparedStatement.setInt(1, pageable.getLimit());
			preparedStatement.setInt(2, pageable.getOffset());
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		ResultSet resultSet = query(request);
		List<Computer> computers = new ArrayList<Computer>();
		while(resultSet.next()) {
			computers.add(resultSetToComputerObject(resultSet));
		}
		return computers;
	}

	public List<Computer> showComputerDetails(int computerId) throws SQLException {
		String request = "SELECT *"
				+ " FROM computer"
				+ " JOIN company ON company.id = computer.company_id"
				+ " WHERE computer.id = " + computerId;

		ResultSet resultSet = query(request);
		List<Computer> computers = new ArrayList<Computer>();
		while(resultSet.next()) {
			computers.add(resultSetToComputerObject(resultSet));
		}
		return computers;
	}
	
	/*
	 * ------------------------------------------
	 * |               Execute FCs              |
	 * ------------------------------------------
	 */
	
	public void deleteComputer(int computerId) {
		String request = "DELETE FROM computer where id = " + computerId;
		execute(request);
	}
	
	public void createComputer(int companyId, String computerName, Date introducedDate, Date discontinuedDate) {
		String request = "INSERT INTO computer VALUES (null, '" + computerName + "', '" + introducedDate + "' , '" + discontinuedDate + "' , '" + companyId + "' )";
		execute(request);
		
	}

	public void updateComputerName(String computerName, int computerId) {
		String request = "UPDATE computer SET name = '" + computerName + "' WHERE id = '" +  computerId + "'";
		execute(request);
		
	}

	public void updateIntroducedDate(Date introducedDate, int computerId) {
		String request = "UPDATE computer SET introduced = '" + introducedDate + "' WHERE id = '" +  computerId + "'";
		execute(request);
		
	}

	public void updateDiscontinuedDate(Date discontinuedDate, int computerId) {
		String request = "UPDATE computer SET discontinued = '" + discontinuedDate + "' WHERE id = '" +  computerId + "'";
		execute(request);
		
	}

	public void updateManufacturer(int companyId, int computerId) {
		String request = "UPDATE computer SET company_id = '" + companyId + "' WHERE id = '" +  computerId + "'";
		execute(request);
		
	}
	
}