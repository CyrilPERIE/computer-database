package fr.excilys.cdb;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "fr.excilys.cdb.database", "fr.excilys.cdb.services",
		"fr.excilys.cdb.servlets", "fr.excilys.cdb.controller", "fr.excilys.cdb.view" })
public class SpringConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
	
	@Bean
	public DataSource dataSource() {
		System.out.println("la");
		return new HikariDataSource(new HikariConfig("/hikariConfig.properties"));
	}

}
