package fr.excilys.cdb.database;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.SpringConfig;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Company;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DAOCompany {
	
	private static final String SELECT_ALL_NAME = "SELECT id, name FROM company";
	private static final String SELECT_ALL_PAGEABLE = "SELECT id, name FROM company limit ? offset ?";
	private static final String SELECT_ID = "SELECT id FROM company WHERE name = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO company VALUES (null, ?)";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	private static final String DELETE_COMPUTER_WHERE_COMPANY = "DELETE FROM computer WHERE company_id = ?";
	@Autowired
	private SpringConfig springConfig;

	/*
	 * ------------------------------------------ 
	 * | 				Query FCs 				|
	 * ------------------------------------------
	 */

	public int getIdCompany(String companyName) throws SQLException, CustomSQLException, ClassNotFoundException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.queryForObject(SELECT_ID, Integer.class, companyName);
	}
	
	public List<Company> listCompanies() throws CustomSQLException, ClassNotFoundException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.query(SELECT_ALL_NAME, new CompanyRowMapper());

	}
	
	public List<Company> listCompaniesPageable(Pageable pageable) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.query(SELECT_ALL_PAGEABLE, new CompanyRowMapper(), pageable.getLimitParameter(), pageable.getOffsetParameter());


	}

	/*
	 * ------------------------------------------ 
	 * | 				Execute FCs 			|
	 * ------------------------------------------
	 */

	public void createCompany(String companyName) throws CustomSQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		jdbcTemplate.update(INSERT_COMPUTER, companyName);
	}
	
	 public void deleteCompany(Company company) throws SQLException, ClassNotFoundException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		jdbcTemplate.update(DELETE_COMPUTER_WHERE_COMPANY, company.getId());
		jdbcTemplate.update(DELETE_COMPANY, company.getId());
	 }
}
