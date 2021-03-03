package fr.excilys.cdb.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pageable {
	private int offsetParameter = 0;
	private int limitParameter = 10;
	private int max;
	
	private final int NUMBER_OF_INDEXES = 5;
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	public Pageable(int max) {
		this.max = max;
	}	
	
	public void next() {
		if(offsetParameter+limitParameter < max) {
			offsetParameter += limitParameter;
		}
	}
	
	public void previous() {
		if(offsetParameter > 0) {
			offsetParameter -= limitParameter;			
		}
	}
	
	public List<Integer> sendPageIndexes() {
	List<Integer> result = new ArrayList<Integer>();

	int count = 1;
	int numberOfIndexWeWant = (max / limitParameter > NUMBER_OF_INDEXES ? NUMBER_OF_INDEXES : max / limitParameter);
	int maxIndex = max / limitParameter;
	int	minIndex = 0;
	int middleIndex = offsetParameter / limitParameter;
	result.add(middleIndex);
	
	boolean sup = true;
	while (count < numberOfIndexWeWant) {
		
		if (sup && Collections.min(result) - 1 >= minIndex) {
			result.add(Collections.min(result) - 1);
			count++;
		} else if (!sup && Collections.max(result) + 1 <= maxIndex) {
			result.add(Collections.max(result) + 1);
			count++;
		}
		sup = !sup;
	}

	Collections.sort(result);
	return result;
}
	
	/*
	 * ------------------------------------------
	 * |             SETTER & GETTER            |
	 * ------------------------------------------
	 */
	
	public int getOffsetParameter() {
		return offsetParameter;
	}
	public void setOffsetParameter(int offsetParameter) {
		this.offsetParameter = offsetParameter;
	}
	public int getLimitParameter() {
		return limitParameter;
	}
	public void setLimitParameter(int limitParameter) {
		this.limitParameter = limitParameter;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
}
