package fr.excilys.cdb.test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandlerTest {
	
	private String username = "admincdb";
	private String password = "qwerty1234";
	private String url = "jdbc:mysql://localhost:3306/cdb";

	private java.sql.Connection connection;
	private static ConnectionHandlerTest connectionHandlerTest;

	private ConnectionHandlerTest() {

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionHandlerTest getConnectionInstance() {
		if (connectionHandlerTest == null) {
			connectionHandlerTest = new ConnectionHandlerTest();
		}
		return connectionHandlerTest;
	}
}
