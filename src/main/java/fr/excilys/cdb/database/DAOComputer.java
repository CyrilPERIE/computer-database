package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Company.CompanyBuilder;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Computer.ComputerBuilder;

public class DAOComputer {

	private static final String PAGEABLE = " limit ? offset ?";
	private static final String SELECT_COMPUTERS_LIKE = "SELECT computer.name, computer.introduced, computer.discontinued, computer.id, company.name "
				+ "FROM computer "
				+ "JOIN company ON company.id = computer.company_id " 
				+ "WHERE computer.name LIKE ? ";
	private static final String UPDATE_COMPUTER = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	private static final String DELETE_COMPUTER = "DELETE FROM computer where id = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer VALUES (null, ?, ?, ?, ? )";
	private static final String SELECT_ONE_COMPUTER = "SELECT computer.name, computer.introduced, computer.discontinued, computer.id, company.name"
			+ " FROM computer"
			+ " LEFT JOIN company ON company.id = computer.company_id" 
			+ " WHERE computer.id = ? ";
	private static final String COUNT_COMPUTERS = "SELECT COUNT(computer.id) "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id " 
			+ "WHERE computer.name LIKE ? ";
	private static final String SELECT_COMPUTERS_PAGEABLE = "SELECT computer.name, computer.introduced, computer.discontinued, computer.id, company.name "
			+ "FROM computer " 
			+ "LEFT JOIN company ON company.id = computer.company_id " 
			+ PAGEABLE;
	private static DAOComputer daoComputer;
	private ConnectionHandler connectionHandler;

	private DAOComputer() {
		this.connectionHandler = ConnectionHandler.getInstance();
	}

	/*
	 * ------------------------------------------ 
	 * |				 SINGLETON 				|
	 * ------------------------------------------
	 */

	public static DAOComputer getInstance() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	/*
	 * ------------------------------------------ 
	 * |			 DIVE IN DB FCs 			|
	 * ------------------------------------------
	 */

	public ResultSet query(String query) throws ClassNotFoundException, CustomSQLException {
		ResultSet resultSet = null;
		try (Connection connection = connectionHandler.openConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException exception) {
			throw new CustomSQLException();
		}
		return resultSet;
	}

	public void execute(String query) throws CustomSQLException, ClassNotFoundException {
		Statement statement;
		try (Connection connection = connectionHandler.openConnection()) {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomSQLException();
		}
	}

	/*
	 * ------------------------------------------ 
	 * | 			ResultSet Cleaning FCs 		|
	 * ------------------------------------------
	 */

	public Computer resultSetToComputerObject(ResultSet resultSet) throws SQLException {
		Computer computer = null;
		try {
			Company company = new CompanyBuilder().companyId(resultSet.getInt("computer.id"))
					.computerName(resultSet.getString("company.name")).build();

			computer = new ComputerBuilder().computerId(resultSet.getInt("computer.id")).computerManufacturer(company)
					.computerName(resultSet.getString("computer.name"))
					.computerIntroducedDate(resultSet.getDate("computer.introduced"))
					.computerDiscontinuedDate(resultSet.getDate("computer.discontinued")).build();

		} catch (SQLException e) {
			throw e;
		}
		return computer;
	}

	private List<Computer> resultSetToList(ResultSet resultSet) throws SQLException {
		List<Computer> computers = new ArrayList<>();
		while (resultSet.next()) {
			computers.add(resultSetToComputerObject(resultSet));
		}
		return computers;
	}

	/*
	 * ------------------------------------------ 
	 * | 				Query FCs 				|
	 * ------------------------------------------
	 */

	public List<Computer> listComputersPageable(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		String request = SELECT_COMPUTERS_PAGEABLE;
		List<Computer> computers = new ArrayList<>();
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setInt(1, pageable.getLimitParameter());
			preparedStatement.setInt(2, pageable.getOffsetParameter());
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = resultSetToList(resultSet);
		} catch (SQLException e) {
			throw new CustomSQLException();
		}

		return computers;

	}
	
	public List<Computer> selectComputersLikePageableOrderBy(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		String request = SELECT_COMPUTERS_LIKE
				+ "ORDER BY " + pageable.getOrderBy()	
				+ PAGEABLE;
		List<Computer> computers = new ArrayList<>();
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, "%" + pageable.getSearch() + "%");
			preparedStatement.setInt(2, pageable.getLimitParameter());
			preparedStatement.setInt(3, pageable.getOffsetParameter());
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = resultSetToList(resultSet);
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}

		return computers;

	}

	public List<Computer> showComputerDetails(int computerId)
			throws SQLException, ClassNotFoundException, CustomSQLException {
		String request = SELECT_ONE_COMPUTER;

		List<Computer> computers = new ArrayList<>();
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setInt(1, computerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = resultSetToList(resultSet);
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}
		return computers;
	}

	public int totalNumberComputer(Pageable pageable) throws ClassNotFoundException, CustomSQLException {
		String request = COUNT_COMPUTERS;
		int result = 0;
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, "%" + pageable.getSearch() + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result = resultSet.getInt("COUNT(computer.id)");
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}

		return result;
	}

	/*
	 * ------------------------------------------ 
	 * | 				Execute FCs				|
	 * ------------------------------------------
	 */

	public void deleteComputer(int computerId) throws ClassNotFoundException, CustomSQLException {
		String request = DELETE_COMPUTER;
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setInt(1, computerId);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}
	}

	public void createComputer(Computer computer) throws CustomSQLException, ClassNotFoundException {
		String request = INSERT_COMPUTER;
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2,
					computer.getIntroducedDate() != null ? Utilitaire.LocalDateToDate(computer.getIntroducedDate())
							: null);
			preparedStatement.setDate(3,
					computer.getDiscontinuedDate() != null ? Utilitaire.LocalDateToDate(computer.getDiscontinuedDate())
							: null);
			preparedStatement.setInt(4, computer.getManufacturer().getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}

	}

	public void updateComputer(Computer computer, int computerId) throws ClassNotFoundException, CustomSQLException {
		String request = UPDATE_COMPUTER;
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2,
					computer.getIntroducedDate() != null ? Utilitaire.LocalDateToDate(computer.getIntroducedDate())
							: null);
			preparedStatement.setDate(3,
					computer.getDiscontinuedDate() != null ? Utilitaire.LocalDateToDate(computer.getDiscontinuedDate())
							: null);
			preparedStatement.setInt(4, computer.getManufacturer().getId());
			preparedStatement.setInt(5, computerId);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new CustomSQLException(e.getMessage());
		}

	}

}
