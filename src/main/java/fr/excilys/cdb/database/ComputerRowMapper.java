package fr.excilys.cdb.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Company.CompanyBuilder;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Computer.ComputerBuilder;

public class ComputerRowMapper implements RowMapper<Computer> {

    public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {

    	Company company = new CompanyBuilder().companyId(resultSet.getInt("computer.id"))
				.companyName(resultSet.getString("company.name")).build();

    	return  new ComputerBuilder().computerId(resultSet.getInt("computer.id")).computerManufacturer(company)
				.computerName(resultSet.getString("computer.name"))
				.computerIntroducedDate(resultSet.getDate("computer.introduced"))
				.computerDiscontinuedDate(resultSet.getDate("computer.discontinued")).build();
        
    }

}
