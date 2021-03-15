package fr.excilys.cdb.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Company.CompanyBuilder;

public class CompanyRowMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {

    	return new CompanyBuilder().companyId(resultSet.getInt("computer.id"))
				.companyName(resultSet.getString("company.name")).build();
        
    }

}
