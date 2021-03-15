package fr.excilys.cdb.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import fr.excilys.cdb.SpringConfig;
import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.model.Computer;

@Component
public class DAOComputer {

	private static final String LEFT_JOIN = "LEFT JOIN company ON company.id = computer.company_id ";
	private static final String FROM_COMPUTER = "FROM computer ";
	private static final String PAGEABLE = " limit ? offset ?";
	private static final String SELECT_COMPUTERS_LIKE = "SELECT computer.name, computer.introduced, computer.discontinued, computer.id, company.name "
				+ FROM_COMPUTER
				+ LEFT_JOIN 
				+ "WHERE computer.name LIKE ? ";
	private static final String UPDATE_COMPUTER = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?";
	private static final String DELETE_COMPUTER = "DELETE FROM computer where id = ?";
	private static final String INSERT_COMPUTER = "INSERT INTO computer VALUES (null, ?, ?, ?, ? )";
	private static final String SELECT_ONE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name, company.id"
			+ " FROM computer"
			+ " LEFT JOIN company ON company.id = computer.company_id" 
			+ " WHERE computer.id = ? ";
	private static final String COUNT_COMPUTERS = "SELECT COUNT(computer.id) "
			+ FROM_COMPUTER
			+ LEFT_JOIN 
			+ "WHERE computer.name LIKE ? ";
	private static final String SELECT_COMPUTERS_PAGEABLE = "SELECT computer.name, computer.introduced, computer.discontinued, computer.id, company.name "
			+ FROM_COMPUTER 
			+ LEFT_JOIN 
			+ PAGEABLE;
	@Autowired
	private SpringConfig springConfig;

	/*
	 * ------------------------------------------ 
	 * | 				Query FCs 				|
	 * ------------------------------------------
	 */
	
	public List<Computer> listComputersPageable(Pageable pageable) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.query(SELECT_COMPUTERS_PAGEABLE, new ComputerRowMapper(), pageable.getLimitParameter(), pageable.getOffsetParameter());
	}
	
	public List<Computer> selectComputersLikePageableOrderBy(Pageable pageable) {
		String request = SELECT_COMPUTERS_LIKE
				+ "ORDER BY " + pageable.getOrderBy()	
				+ PAGEABLE;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.query(request, new ComputerRowMapper(), pageable.getLimitParameter(), pageable.getOffsetParameter());
		
	}

	public List<Computer> showComputerDetails(int computerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.query(SELECT_ONE_COMPUTER, new ComputerRowMapper(), computerId);
	}

	public int totalNumberComputer(Pageable pageable) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		return jdbcTemplate.queryForObject(COUNT_COMPUTERS, Integer.class, "%" + pageable.getSearch() + "%");
	}

	/*
	 * ------------------------------------------ 
	 * | 				Execute FCs				|
	 * ------------------------------------------
	 */

	public void deleteComputer(int computerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		jdbcTemplate.update(DELETE_COMPUTER, computerId);
	}	

	public void createComputer(Computer computer) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		jdbcTemplate.update(INSERT_COMPUTER, computer.getName(),
				computer.getIntroducedDate() != null ? Utilitaire.LocalDateToDate(computer.getIntroducedDate())	: null,
				computer.getDiscontinuedDate() != null ? Utilitaire.LocalDateToDate(computer.getDiscontinuedDate())	: null,
				computer.getManufacturer().getId());

	}
	
	public void updateComputer(Computer computer, int computerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(springConfig.dataSource());
		jdbcTemplate.update(UPDATE_COMPUTER, computer.getName(),
				computer.getIntroducedDate() != null ? Utilitaire.LocalDateToDate(computer.getIntroducedDate())	: null,
				computer.getDiscontinuedDate() != null ? Utilitaire.LocalDateToDate(computer.getDiscontinuedDate())	: null,
				computer.getManufacturer().getId(),
				computerId);

	}

}
