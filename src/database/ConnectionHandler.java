package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHandler {
	
	private String username = "admincdb";
	private String password = "qwerty1234";
	private String url = "jdbc:mysql://localhost:3306/computer-database-db";

	private java.sql.Connection connection;
	private static ConnectionHandler connectionHandler;

	private ConnectionHandler() {

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionHandler getConnectionInstance() {
		if (connectionHandler == null) {
			connectionHandler = new ConnectionHandler();
		}
		return connectionHandler;
	}

	public ResultSet query(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException exception) {
			
			exception.printStackTrace();
		}
		return resultSet;
	}
	
	public void execute(String query) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ------------------------------------------
	 * |             SETTER & GETTER            |
	 * ------------------------------------------
	 */
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public java.sql.Connection getConnection() {
		return connection;
	}

	public void setConnection(java.sql.Connection connection) {
		this.connection = connection;
	}

	public static ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public static void setConnectionHandler(ConnectionHandler connectionHandler) {
		ConnectionHandler.connectionHandler = connectionHandler;
	}
	
	
	
}
