package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.cdb.model.Company;

public class DAOCompany {
	
	private static DAOCompany daoCompany;
	private ConnectionHandler connectionHandler;
	
	private DAOCompany() {
		this.connectionHandler = ConnectionHandler.getConnectionInstance();
	}

	/*
	 * ------------------------------------------
	 * |               SINGLETON                |
	 * ------------------------------------------
	 */
	
	public static DAOCompany getDAOCompanyInstance() {
		if(daoCompany == null) {
			daoCompany = new DAOCompany();
		}
		return daoCompany;
	}
	
	/*
	 * ------------------------------------------
	 * |       ResultSet Cleaning FCs           |
	 * ------------------------------------------
	 */	
	
	private Company resultSetToCompanyObject(ResultSet resultSet) throws SQLException {
		Company company = null;
		try {
			company = new Company.CompanyBuilder().computerId(resultSet.getInt("id"))
					.computerName(resultSet.getString("name")).build();

		} catch (SQLException e) {
			throw e;
		}
		return company;
	}
	

	private List<Company> resultSetToList(ResultSet resultSet) throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		while(resultSet.next()) {
			companies.add(resultSetToCompanyObject(resultSet));
		}
		return companies;
	}

	/*
	 * ------------------------------------------
	 * |            DIVE IN DB FCs              |
	 * ------------------------------------------
	 */
	
	public List<Company> query(String request) {
		List<Company> companies = new ArrayList<Company>();
		try(Connection connection = connectionHandler.openConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(request);
			companies = resultSetToList(resultSet);
		} catch (SQLException exception) {
			
			exception.printStackTrace();
		}
		return companies;
	}
	
	public List<Company> queryPreparedStatement(PreparedStatement preparedStatement) {
		List<Company> companies = new ArrayList<Company>();
		try(Connection connection = connectionHandler.openConnection()) {
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = resultSetToList(resultSet);
		} catch (SQLException exception) {
			
			exception.printStackTrace();
		}
		return companies;
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
	 * |               Query FCs                |
	 * ------------------------------------------
	 */
	
	public List<Company> listCompanies() throws SQLException {
		String request = "SELECT * FROM company";
		return query(request);
	}
	
	public int getIdCompany(String companyName) throws SQLException {
		String request = "SELECT id FROM company WHERE name = '" + companyName +"'";
		return query(request).get(0).getId();
	}

	public List<Company> listCompaniesPageable(Pageable pageable) {
		String request = "SELECT * FROM company limit ? offset ?";
		List<Company> companies = new ArrayList<Company>();
		try(Connection connection = connectionHandler.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(request)){
			preparedStatement.setInt(1, pageable.getLimit());
			preparedStatement.setInt(2, pageable.getOffset());
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = resultSetToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return companies;
		
	}

	/*
	 * ------------------------------------------
	 * |               Execute FCs              |
	 * ------------------------------------------
	 */
	
	public void createCompany(String companyName) {
		String request = "INSERT INTO company VALUES (null, '" + companyName + "')";
		execute(request);
	}
	
}
