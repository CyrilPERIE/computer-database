package fr.excilys.cdb.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Pageable {
	
	private int offsetParameter = 0;
	private int limitParameter = 10;
	private int maxComputer;
	private String search;
	private String orderBy;
	
	private final int NUMBER_OF_INDEXES = 5;
	
	/*
	 * ------------------------------------------
	 * |             CONSTRUCTOR & FCs          |
	 * ------------------------------------------
	 */
	
	public Pageable() {
		this.search = "";
		this.orderBy = "computer.id";
	}	
	
	public void next() {
		if(offsetParameter+limitParameter < maxComputer) {
			offsetParameter += limitParameter;
		}
	}
	
	public void previous() {
		if(offsetParameter > 0) {
			offsetParameter -= limitParameter;			
		}
	}
	
	public List<Integer> sendPageIndexes() {
	List<Integer> result = new ArrayList<>();

	int count = 1;
	int numberOfIndexWeWant = (maxComputer / limitParameter > NUMBER_OF_INDEXES ? NUMBER_OF_INDEXES : maxComputer / limitParameter);
	int maxIndex = maxComputer / limitParameter;
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
	
	public void reset() {
		offsetParameter = 0;
		this.orderBy = "computer.id";
		this.limitParameter = 10;
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
	public int getMaxComputer() {
		return maxComputer;
	}
	public void setMaxComputer(int max) {
		this.maxComputer = max;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		if(search != this.search) {
			reset();
		}
		if(search == null) {
			this.search = "";
		}
		else {
			this.search = search;			
		}
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
