package fr.excilys.cdb.dto.mapper;

import java.text.ParseException;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.dto.AddComputerFormOutput;
import fr.excilys.cdb.exception.ParseError;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Company.CompanyBuilder;
import fr.excilys.cdb.model.Computer.ComputerBuilder;

public class DTOComputerMapper {
	
	public static Computer AddComputerFormOutputToComputer(AddComputerFormOutput addComputerFormOutput) throws ParseError{
		try {

			Company company = new CompanyBuilder()
					.companyId(Integer.valueOf(addComputerFormOutput.getCompanyId()))
					.build();
			
			Computer computer = new ComputerBuilder()
					.computerManufacturer(company)
					.computerName(addComputerFormOutput.getComputerName())
					.computerIntroducedDate(Utilitaire.stringToDateWebUI(addComputerFormOutput.getIntroducedDate()))
					.computerDiscontinuedDate(Utilitaire.stringToDateWebUI(addComputerFormOutput.getDiscontinuedDate()))
					.build();

			return computer;
		} catch(ParseException e) {
			throw new ParseError();
		}
	}
}
