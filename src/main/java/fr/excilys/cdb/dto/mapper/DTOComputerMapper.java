package fr.excilys.cdb.dto.mapper;

import java.text.ParseException;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.dto.AddComputerFormOutput;
import fr.excilys.cdb.dto.EditComputerFormInput;
import fr.excilys.cdb.exception.ParseError;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Company.CompanyBuilder;
import fr.excilys.cdb.model.Computer.ComputerBuilder;

public class DTOComputerMapper {
	
	public static Computer addComputerFormOutputToComputer(AddComputerFormOutput addComputerFormOutput) throws ParseError{
		try {
			Company company = new CompanyBuilder()
					.companyId(Integer.valueOf(addComputerFormOutput.getCompanyId()))
					.build();
			
			Computer computer = new ComputerBuilder()
					.computerManufacturer(company)
					.computerName(addComputerFormOutput.getComputerName())
					.computerIntroducedDate(addComputerFormOutput.getIntroducedDate().isEmpty() ? null: Utilitaire.stringToDateWebUI(addComputerFormOutput.getIntroducedDate()))
					.computerDiscontinuedDate(addComputerFormOutput.getDiscontinuedDate().isEmpty() ? null: Utilitaire.stringToDateWebUI(addComputerFormOutput.getDiscontinuedDate()))
					.build();

			return computer;
		} catch(ParseException e) {
			System.out.println(e.getMessage());
			throw new ParseError();
		}
	}
	
	public static Computer editComputerFormOutputToComputer(EditComputerFormInput editComputerFormInput) throws ParseError{
		try {
			Company company = new CompanyBuilder()
					.companyId(Integer.valueOf(editComputerFormInput.getCompanyId()))
					.build();
			
			Computer computer = new ComputerBuilder()
					.computerId(Integer.valueOf(editComputerFormInput.getComputerId()))
					.computerManufacturer(company)
					.computerName(editComputerFormInput.getComputerName())
					.computerIntroducedDate(editComputerFormInput.getIntroducedDate().isEmpty() ? null: Utilitaire.stringToDateWebUI(editComputerFormInput.getIntroducedDate()))
					.computerDiscontinuedDate(editComputerFormInput.getDiscontinuedDate().isEmpty() ? null: Utilitaire.stringToDateWebUI(editComputerFormInput.getDiscontinuedDate()))
					.build();

			return computer;
		} catch(ParseException e) {
			System.out.println(e.getMessage());
			throw new ParseError();
		}
	}
	
}
