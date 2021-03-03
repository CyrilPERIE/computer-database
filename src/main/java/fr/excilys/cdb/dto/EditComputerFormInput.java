package fr.excilys.cdb.dto;
public class EditComputerFormInput {
	public String computerName;
	public String introducedDate;
	public String discontinuedDate;
	public String companyId;
	public String companyName;
	public String computerId;
	
	/*
	 * ------------------------------------------ 
	 * | 				CONSTRUCTOR				|
	 * ------------------------------------------
	 */
	
	private EditComputerFormInput(EditComputerFormInputBuilder editComputerFormInputBuilder) {
		this.computerName = editComputerFormInputBuilder.computerName;
		this.computerId = editComputerFormInputBuilder.computerId;
		this.introducedDate = editComputerFormInputBuilder.introducedDate;
		this.discontinuedDate = editComputerFormInputBuilder.discontinuedDate;
		this.companyId = editComputerFormInputBuilder.companyId;
		this.companyName = editComputerFormInputBuilder.companyName;
	}
	
	/*
	 * ------------------------------------------ 
	 * | 			BUILDER PATTERN 			|
	 * ------------------------------------------
	 */
	
	public static final class EditComputerFormInputBuilder {
		private String computerName;
		private String computerId;
		private String introducedDate;
		private String discontinuedDate;
		private String companyId;
		private String companyName;

		public EditComputerFormInputBuilder withComputerName(String computerName) {
			this.computerName = computerName;
			return this;
		}
		
		public EditComputerFormInputBuilder withComputerId(String computerId) {
			this.computerId = computerId;
			return this;
		}

		public EditComputerFormInputBuilder withIntroducedDate(String introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public EditComputerFormInputBuilder withDiscontinuedDate(String discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}

		public EditComputerFormInputBuilder withCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public EditComputerFormInputBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public EditComputerFormInput build() {
			return new EditComputerFormInput(this);
		}
	}
	
	public String toString() {
		return "computer name : " + computerName + 
				" | introduced date : " + introducedDate + 
				" | discontinued date : " + discontinuedDate + 
				" | company id : " + companyId + 
				" | company name : " + companyName;
	}

	/*
	 * ------------------------------------------ 
	 * | 			SETTER & GETTER 			|
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComputerId() {
		return computerId;
	}

	public void setComputerId(String computerId) {
		this.computerId = computerId;
	}
}
