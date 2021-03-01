package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.exception.CustomSQLException;
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
			Company company = new CompanyBuilder().companyId(resultSet.getInt("computer.id"))
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
	
	private List<Computer> resultSetToList(ResultSet resultSet) throws SQLException {
		List<Computer> computers = new ArrayList<Computer>();
		while(resultSet.next()) {
			computers.add(resultSetToComputerObject(resultSet));
		}
		return computers;
	}
	
	/*
	 * ------------------------------------------
	 * |               Query FCs                |
	 * ------------------------------------------
	 */
	
	public List<Computer> listComputersPageable(Pageable pageable) throws SQLException {
		String request = "SELECT * "
				+ "FROM computer "
				+ "LEFT JOIN company ON company.id = computer.company_id "
				+ "limit ? offset ?";
		List<Computer> computers = new ArrayList<Computer>();
		try(Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setInt(1, pageable.getLimit());
			preparedStatement.setInt(2, pageable.getOffset());
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = resultSetToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return computers;
		
	}

	public List<Computer> showComputerDetails(int computerId) throws SQLException {
		String request = "SELECT *"
				+ " FROM computer"
				+ " JOIN company ON company.id = computer.company_id"
				+ " WHERE computer.id = ? ";

		List<Computer> computers = new ArrayList<Computer>();
		try(Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setInt(1, computerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = resultSetToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return computers;
	}
	


	public int totalNumberComputer() {
		String request = "SELECT "
				+ " COUNT(id)"
				+ " FROM computer";
		int result = 0;
		try(Connection connection = connectionHandler.openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(request)){
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result = resultSet.getInt("COUNT(id)");
		} catch (SQLException e) {
			e.printStackTrace();
} 
			
		return result;
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
	
	public void createComputer(Computer computer) throws CustomSQLException {
		String request = "INSERT INTO computer VALUES (null, ?, ?, ?, ? )";
		try(Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, computer.getIntroducedDate()!= null ? Utilitaire.LocalDateToDate(computer.getIntroducedDate()) : null);
			preparedStatement.setDate(3, computer.getDiscontinuedDate()!= null ? Utilitaire.LocalDateToDate(computer.getDiscontinuedDate()) : null);
			preparedStatement.setInt(4, computer.getManufacturer().getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new CustomSQLException();
		} 
		
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
