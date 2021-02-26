package fr.excilys.cdb.database;

public class Pageable {
	private int offset = 0;
	private int limit = 10;
	private int max;
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	public Pageable(int max) {
		this.max = max;
	}	
	
	public void next() {
		if(offset < max) {
			offset += limit;
		}
	}
	
	public void previous() {
		if(offset > 0) {
			offset -= limit;			
		}
	}
	
	/*
	 * ------------------------------------------
	 * |             SETTER & GETTER            |
	 * ------------------------------------------
	 */
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	
}
