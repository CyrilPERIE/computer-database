package database;

public class Pageable {
	private int offset = 0;
	private int limit = 10;
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	public Pageable() {
	}	
	
	public void next() {
		offset += limit;
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
	
	
}
