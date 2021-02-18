package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DAOComputer {
	
	private static DAOComputer queriesComputer;
	private ConnectionHandler connectionHandler;
	
	private DAOComputer() {
		connectionHandler = ConnectionHandler.getConnectionInstance();
	}
	
	public static DAOComputer getQueriesComputerInstance() {
		if(queriesComputer == null) {
			queriesComputer = new DAOComputer();
		}
		return queriesComputer;
	}

	public ResultSet listComputers() {
		String request = "SELECT *"
				+ " FROM computer"
				+ " JOIN company ON company.id = computer.company_id";
		return connectionHandler.query(request);
	}
	
	public ResultSet listComputersPageable(Pageable pageable) {
		ResultSet resultSet = null;
		Connection connection = connectionHandler.getConnection();
		String request = "SELECT * FROM computer JOIN company ON company.id = computer.company_id limit ? offset ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			preparedStatement.setInt(1, pageable.getLimit());
			preparedStatement.setInt(2, pageable.getOffset());
			resultSet = preparedStatement.executeQuery(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultSet;
	}

	public ResultSet showComputerDetails(int computerId) {
		String request = "SELECT *"
				+ " FROM computer"
				+ " JOIN company ON company.id = computer.company_id"
				+ " WHERE computer.id = " + computerId;
		return connectionHandler.query(request);
	}

	public void deleteComputer(int computerId) {
		String request = "DELETE FROM computer where id = " + computerId;
		connectionHandler.execute(request);
	}
	
	public void createComputer(int companyId, String computerName, Date introducedDate, Date discontinuedDate) {
		String request = "INSERT INTO computer VALUES (null, '" + computerName + "', '" + introducedDate + "' , '" + discontinuedDate + "' , '" + companyId + "' )";
		connectionHandler.execute(request);
		
	}

	public void updateComputerName(String computerName, int computerId) {
		String request = "UPDATE computer SET name = '" + computerName + "' WHERE id = '" +  computerId + "'";
		connectionHandler.execute(request);
		
	}

	public void updateIntroducedDate(Date introducedDate, int computerId) {
		String request = "UPDATE computer SET introduced = '" + introducedDate + "' WHERE id = '" +  computerId + "'";
		connectionHandler.execute(request);
		
	}

	public void updateDiscontinuedDate(Date discontinuedDate, int computerId) {
		String request = "UPDATE computer SET discontinued = '" + discontinuedDate + "' WHERE id = '" +  computerId + "'";
		connectionHandler.execute(request);
		
	}

	public void updateManufacturer(int companyId, int computerId) {
		String request = "UPDATE computer SET company_id = '" + companyId + "' WHERE id = '" +  computerId + "'";
		connectionHandler.execute(request);
		
	}
	
}
