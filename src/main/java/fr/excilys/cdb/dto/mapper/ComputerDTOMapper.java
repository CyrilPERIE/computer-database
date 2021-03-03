package fr.excilys.cdb.dto.mapper;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.dto.EditComputerFormInput;
import fr.excilys.cdb.model.Computer;

public class ComputerDTOMapper {

	public static EditComputerFormInput computerToAddComputerFormOutput(Computer computer) {
		
		EditComputerFormInput editComputerFormInput = new EditComputerFormInput.EditComputerFormInputBuilder()
																.withComputerId(String.valueOf(computer.getId()))
																.withComputerName(computer.getName())
																.withIntroducedDate(Utilitaire.localDateToString(computer.getIntroducedDate()))
																.withDiscontinuedDate(Utilitaire.localDateToString(computer.getDiscontinuedDate()))
																.withCompanyId(String.valueOf(computer.getManufacturer().getId()))
																.withCompanyName(computer.getManufacturer().getName())
																.build();
		return editComputerFormInput;
		
	}
}
