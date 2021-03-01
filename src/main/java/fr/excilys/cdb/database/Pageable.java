package fr.excilys.cdb.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public List<Integer> sendPageIndexes() {
	List<Integer> result = new ArrayList<Integer>();

	int count = 1, pageableOffset = this.getOffset(), pageableLimit = this.getLimit(),
			pageableMax = this.getMax(),
			numberOfIndexWeWant = (pageableMax / pageableLimit > 6 ? 5 : pageableMax / pageableLimit),
			maxIndex = pageableMax / pageableLimit, 
			minIndex = 0, 
			middleIndex = pageableOffset / pageableLimit;
	result.add(middleIndex);
	
	boolean sup = true;
	while (count < numberOfIndexWeWant) {
		
		if (sup && Collections.min(result) - 1 >= minIndex) {
			result.add(Collections.min(result) - 1);
			count++;
			sup = !sup;
		} else if (!sup && Collections.max(result) + 1 <= maxIndex) {
			result.add(Collections.max(result) + 1);
			count++;
			sup = !sup;
		}
		else {
			sup = !sup;
		}
	}

	Collections.sort(result);
	return result;
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
