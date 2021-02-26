package fr.excilys.cdb.dto;

public class DTOAddComputerForm {
	private String computerName;
	private String introducedDate;
	private String discontinuedDate;
	private String companyId;


	private DTOAddComputerForm(DTOAddComputerFormBuilder dtoAddComputerFormBuilder) {
		this.computerName = dtoAddComputerFormBuilder.computerName;
		this.introducedDate = dtoAddComputerFormBuilder.introducedDate;
		this.discontinuedDate = dtoAddComputerFormBuilder.discontinuedDate;
		this.companyId = dtoAddComputerFormBuilder.companyId;
	}

	/*
	 * ------------------------------------------
	 * |          		BUILDER 	            |
	 * ------------------------------------------
	 */
	
	public static DTOAddComputerFormBuilder builder() {
		return new DTOAddComputerFormBuilder();
	}
	
	public static final class DTOAddComputerFormBuilder {
		private String computerName;
		private String introducedDate;
		private String discontinuedDate;
		private String companyId;

		private DTOAddComputerFormBuilder() {
		}

		public DTOAddComputerFormBuilder withComputerName(String computerName) {
			this.computerName = computerName;
			return this;
		}

		public DTOAddComputerFormBuilder withIntroducedDate(String introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public DTOAddComputerFormBuilder withDiscontinuedDate(String discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}

		public DTOAddComputerFormBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public DTOAddComputerForm build() {
			return new DTOAddComputerForm(this);
		}
	}
	
	
	/*
	 * ------------------------------------------
	 * |          SETTERS & GETTERS             |
	 * ------------------------------------------
	 */
	
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
	
	
}
