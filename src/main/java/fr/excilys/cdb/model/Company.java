package fr.excilys.cdb.model;

public class Company {
	
	/*
	 * ------------------------------------------
	 * |             BUILDER PATTERN            |
	 * ------------------------------------------
	 */
	
	public static class CompanyBuilder {
		private int id;
		private String name;
		
		public CompanyBuilder() {
			
		}
		
		public CompanyBuilder companyId(int id) {
			this.id = id;
			return this;
		}
		
		public CompanyBuilder computerName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			Company companyTestBuilder = new Company();
			companyTestBuilder.setId(this.id);
			companyTestBuilder.setName(this.name);
			return companyTestBuilder;
		}
	}
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	private int id;
	private String name;
	
	private Company() {
		
	}
	
	/*
	 * ------------------------------------------
	 * |             SETTER & GETTER            |
	 * ------------------------------------------
	 */
	
	public String toString() {	
		return "The manufacturer n°" + this.getId() + ": " + this.getName();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
