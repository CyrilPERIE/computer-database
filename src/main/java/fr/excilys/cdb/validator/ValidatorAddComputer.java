package fr.excilys.cdb.validator;

public class ValidatorAddComputer {
	
	public boolean isComputerName(String computerName) {
		return true;
	}
	
	public boolean isIntroducedDate(String introducedDate) {
		return true;
	}
	
	public boolean isDiscontinuedDate(String discontinuedDate) {
		return true;
	}
	
	public boolean isCompanyId(String companyId) {
		return true;
	}
	
	public boolean isDiscontinuedDateAfterIntroducedDate(String introducedDate, String discontinuedDate) {
		return true;
	}
}
