package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCompany {
	
	private static DAOCompany queriesCompany;
	private ConnectionHandler connectionHandler;
	
	private DAOCompany() {
		connectionHandler = ConnectionHandler.getConnectionInstance();
	}
	
	public static DAOCompany getQueriesCompanyInstance() {
		if(queriesCompany == null) {
			queriesCompany = new DAOCompany();
		}
		return queriesCompany;
	}
	
	public ResultSet listCompanies() {
		String request = "SELECT * FROM company";
		return connectionHandler.query(request);
	}
	
	public void createCompany(String companyName) {
		String request = "INSERT INTO company VALUES (null, '" + companyName + "')";
		connectionHandler.execute(request);
	}
	

	public int getIdCompany(String companyName) {
		String request = "SELECT id FROM company WHERE name = '" + companyName +"'";
		ResultSet resultSet = connectionHandler.query(request);
		int idCompany = 0;
		try {
			resultSet.next();
			idCompany = resultSet.getInt("id");
		}
		catch(SQLException e) {
			createCompany(companyName);
			idCompany = getIdCompany(companyName);
		}
		
		return idCompany;
	}

	public ResultSet listComputersPageable(int offset, int limit) {
		
		ResultSet resultSet = null;
		Connection connection = connectionHandler.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM company limit ? offset ?");
			preparedStatement.setInt(1, limit);
			preparedStatement.setInt(2, offset);
			resultSet = preparedStatement.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return resultSet;
		
	}
	
}
