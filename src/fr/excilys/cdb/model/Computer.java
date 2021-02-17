package fr.excilys.cdb.model;

import java.sql.Date;
import java.time.LocalDate;

public class Computer {	
	
	/*
	 * ------------------------------------------
	 * |             BUILDER PATTERN            |
	 * ------------------------------------------
	 */

	public static class ComputerBuilder {

		private int id;
		private Company manufacturer;
		private String name;
		private LocalDate introducedDate;
		private LocalDate discontinuedDate;

		public ComputerBuilder() {

		}

		public ComputerBuilder computerId(int id) {
			this.id = id;

			return this;
		}

		public ComputerBuilder computerManufacturer(Company manufacturer) {
			this.manufacturer = manufacturer;

			return this;
		}

		public ComputerBuilder computerName(String name) {
			this.name = name;

			return this;
		}

		public ComputerBuilder computerIntroducedDate(Date introducedDate) {
			try {
				this.introducedDate = introducedDate.toLocalDate();
			}
			catch(Exception e) {
				this.introducedDate = null;
			}
			return this;

		}

		public ComputerBuilder computerDiscontinuedDate(Date discontinuedDate) {
			try {
				this.discontinuedDate = discontinuedDate.toLocalDate();
			}
			catch(Exception e) {
				this.discontinuedDate = null;
			}
			return this;
		}

		public Computer build() {
			Computer computerTestBuilder = new Computer();
			computerTestBuilder.id = this.id;
			computerTestBuilder.manufacturer = this.manufacturer;
			computerTestBuilder.name = this.name;
			computerTestBuilder.introducedDate = this.introducedDate;
			computerTestBuilder.discontinuedDate = this.discontinuedDate;
			return computerTestBuilder;
		}

	}
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	private int id;
	private Company manufacturer;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;	
	
	private Computer() {
		
	}
	
	public String toString() {
		if(this.discontinuedDate == null) {
			return id + " | " + this.name + " has been manufacturing by " + manufacturer.getName() + " and has been introduced in " + this.dateToString(this.getIntroducedDate()) + ", it's still on the market";
		}
		return id + " | " + this.name + " has been manufacturing by " + manufacturer.getName() + " and has been introduced in " + this.dateToString(this.getIntroducedDate()) + " and discontinued in " + this.dateToString(this.getDiscontinuedDate());
	}

	private String dateToString(LocalDate date) {
		try {
			String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
			return date.getDayOfWeek() + " of " + months[date.getMonthValue()-1] + " " + date.getYear();
		}
		catch(Exception e) {
			return "NO DATE";
		}
		
	}
	
	/*
	 * ------------------------------------------
	 * |             SETTER & GETTER            |
	 * ------------------------------------------
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public void listElements() {
		System.out.println("1 manufacturer : " + manufacturer.getName());
		System.out.println("2 computer name : " + name);
		System.out.println("3 introducedDate : " + introducedDate);
		System.out.println("4 discontinuedDate : " + discontinuedDate);
		
	}
}
