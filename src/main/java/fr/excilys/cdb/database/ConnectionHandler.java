package fr.excilys.cdb.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConnectionHandler {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource datasource;

	public void openConnection() {
		ConnectionHandlerProperties connectionHandlerProperties = ConnectionHandlerProperties.getInstance();
		config.setDriverClassName(connectionHandlerProperties.getDriver());
		config.setJdbcUrl(connectionHandlerProperties.getUrl());
		config.setUsername(connectionHandlerProperties.getLogin());
		config.setPassword(connectionHandlerProperties.getPassword());
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		datasource = new HikariDataSource(config);
	}

	Connection getConnection() throws SQLException {
		if (datasource == null || datasource.isClosed()) {
			openConnection();
		}
		return datasource.getConnection();
	}
}
