package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
	
	private String username = "admincdb";
	private String password = "qwerty1234";
	private String url = "jdbc:mysql://localhost:3306/computer-database-db";

	private static ConnectionHandler connectionHandler;

	private ConnectionHandler() {
	}
	
	public Connection openConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
	/*
	 * ------------------------------------------
	 * |               SINGLETON                |
	 * ------------------------------------------
	 */
	
	public static ConnectionHandler getConnectionInstance() {
		if (connectionHandler == null) {
			connectionHandler = new ConnectionHandler();
		}
		return connectionHandler;
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

	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public void setConnectionHandler(ConnectionHandler connectionHandler) {
		ConnectionHandler.connectionHandler = connectionHandler;
	}
	
	
	
}
