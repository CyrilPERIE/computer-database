package fr.excilys.cdb.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionHandlerProperties {
	
	private static ConnectionHandlerProperties INSTANCE;
	private String PATH_PROPERTIES = "database.properties";
	private InputStream inputStream;
	Properties properties = new Properties();
	
	private String url;
	private String login;
	private String password;
	private String driver;
	
	
	/*
	 * ------------------------------------------
	 * |              CONSTRUCTEUR              |
	 * ------------------------------------------
	 */
	
	private ConnectionHandlerProperties() {
		
		inputStream = this.getClass().getClassLoader().getResourceAsStream(PATH_PROPERTIES);
		
		if(inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		
		setUrl(properties.getProperty("url"));
		setLogin(properties.getProperty("login"));
		setPassword(properties.getProperty("password"));
		setDriver(properties.getProperty("driver"));
		
	}
	
	
	/*
	 * ------------------------------------------
	 * |                SINGLETION              |
	 * ------------------------------------------
	 */

	public static synchronized ConnectionHandlerProperties getInstance() {
		if(ConnectionHandlerProperties.INSTANCE == null) {
			ConnectionHandlerProperties.INSTANCE = new ConnectionHandlerProperties();
		}
	return ConnectionHandlerProperties.INSTANCE;
	}
	

	
	public String getUrl() {
		return url;
	}

	/*
	 * ------------------------------------------
	 * |            SETTERS & GETTERS           |
	 * ------------------------------------------
	 */
	
	public void setUrl(String url) {
		this.url = url;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}


	public void setDriver(String driver) {
		this.driver = driver;
	}
}
