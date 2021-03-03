package fr.excilys.cdb.dto;

public class AddComputerFormOutput {
	private String computerName;
	private String introducedDate;
	private String discontinuedDate;
	private String companyId;

	/*
	 * ------------------------------------------ 
	 * | 				CONSTRUCTOR				|
	 * ------------------------------------------
	 */
	
	private AddComputerFormOutput(AddComputerFormOutputBuilder addComputerFormOutputBuilder) {
		this.computerName = addComputerFormOutputBuilder.computerName;
		this.introducedDate = addComputerFormOutputBuilder.introducedDate;
		this.discontinuedDate = addComputerFormOutputBuilder.discontinuedDate;
		this.companyId = addComputerFormOutputBuilder.companyId;
	}
	
	/*
	 * ------------------------------------------
	 * |          		BUILDER 	            |
	 * ------------------------------------------
	 */
	
	public static final class AddComputerFormOutputBuilder {
		private String computerName;
		private String introducedDate;
		private String discontinuedDate;
		private String companyId;

		public AddComputerFormOutputBuilder withComputerName(String computerName) {
			this.computerName = computerName;
			return this;
		}

		public AddComputerFormOutputBuilder withIntroducedDate(String introducedDate) {
			this.introducedDate = introducedDate;	
			return this;
		}

		public AddComputerFormOutputBuilder withDiscontinuedDate(String discontinuedDate) {
			this.discontinuedDate = discontinuedDate;			
			return this;
		}

		public AddComputerFormOutputBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public AddComputerFormOutput build() {
			return new AddComputerFormOutput(this);
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
	
	public String toString() {
		return "computer name " + computerName + " | introduced date " + introducedDate + " | discontinued date " + discontinuedDate + " | company id " + companyId;
	}
	
}
