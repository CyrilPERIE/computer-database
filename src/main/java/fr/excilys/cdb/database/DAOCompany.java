package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Company;

public class DAOCompany {
	
	private static final String SELECT_ALL_NAME = "SELECT id, name FROM company";
	private static final String SELECT_ALL_PAGEABLE = "SELECT id, name FROM company limit ? offset ?";
	private static final String SELECT_ID = "SELECT id FROM company WHERE name = ?";
	private static DAOCompany daoCompany;
	private ConnectionHandler connectionHandler;

	private DAOCompany() {
		this.connectionHandler = ConnectionHandler.getInstance();
	}

	/*
	 * ------------------------------------------ | SINGLETON |
	 * ------------------------------------------
	 */

	public static DAOCompany getInstance() {
		if (daoCompany == null) {
			daoCompany = new DAOCompany();
		}
		return daoCompany;
	}

	/*
	 * ------------------------------------------ | ResultSet Cleaning FCs |
	 * ------------------------------------------
	 */

	private Company resultSetToCompanyObject(ResultSet resultSet) throws SQLException {
		Company company = null;
		try {
			company = new Company.CompanyBuilder().companyId(resultSet.getInt("id"))
					.computerName(resultSet.getString("name")).build();

		} catch (SQLException e) {
			throw e;
		}
		return company;
	}

	private List<Company> resultSetToList(ResultSet resultSet) throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		while (resultSet.next()) {
			companies.add(resultSetToCompanyObject(resultSet));
		}
		return companies;
	}

	/*
	 * ------------------------------------------ | DIVE IN DB FCs |
	 * ------------------------------------------
	 */

	public List<Company> query(String request) throws ClassNotFoundException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = connectionHandler.openConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(request);
			companies = resultSetToList(resultSet);
		} catch (SQLException exception) {

			exception.printStackTrace();
		}
		return companies;
	}

	public List<Company> queryPreparedStatement(PreparedStatement preparedStatement) throws ClassNotFoundException, CustomSQLException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = connectionHandler.openConnection()) {
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = resultSetToList(resultSet);
		} catch (SQLException exception) {
			throw new CustomSQLException();
		}
		return companies;
	}

	public void execute(String query) throws ClassNotFoundException, CustomSQLException {
		Statement statement;
		try (Connection connection = connectionHandler.openConnection()) {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomSQLException();
		}
	}

	/*
	 * ------------------------------------------ | Query FCs |
	 * ------------------------------------------
	 */

	public int getIdCompany(String companyName) throws SQLException, CustomSQLException, ClassNotFoundException {
		String request = SELECT_ID;
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, companyName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int result = resultSet.getInt("id");
			return result;
		} catch (Exception e) {
			createCompany(companyName);
			return getIdCompany(companyName);
		}
	}
	
	public List<Company> listCompanies() throws CustomSQLException, ClassNotFoundException {
		String request = SELECT_ALL_NAME;
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = resultSetToList(resultSet);
		} catch (SQLException e) {
			throw new CustomSQLException();
		}
		return companies;

	}
	
	public List<Company> listCompaniesPageable(Pageable pageable) throws CustomSQLException, ClassNotFoundException {
		String request = SELECT_ALL_PAGEABLE;
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setInt(1, pageable.getLimit());
			preparedStatement.setInt(2, pageable.getOffset());
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = resultSetToList(resultSet);
		} catch (SQLException e) {
			throw new CustomSQLException();
		}
		return companies;

	}

	/*
	 * ------------------------------------------ | Execute FCs |
	 * ------------------------------------------
	 */

	public void createCompany(String companyName) throws CustomSQLException, ClassNotFoundException {
		String request = "INSERT INTO company VALUES (null, ?)";
		try (Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)) {
			preparedStatement.setString(1, companyName);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new CustomSQLException();
		}
	}

}
