package com.covideinfo.bo;

/**
 * @author swami.tammireddi
 *
 */
public class SampleCollectionDashBoardColumns implements Comparable<SampleCollectionDashBoardColumns>{
	private int count;
	private String columnValue;
	private int noOfVacutainers;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	public int getNoOfVacutainers() {
		return noOfVacutainers;
	}
	public void setNoOfVacutainers(int noOfVacutainers) {
		this.noOfVacutainers = noOfVacutainers;
	}
	public SampleCollectionDashBoardColumns() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SampleCollectionDashBoardColumns(int count, String columnValue, int noOfVacutainers) {
		super();
		this.count = count;
		this.columnValue = columnValue;
		this.noOfVacutainers = noOfVacutainers;
	}
	@Override
	public int compareTo(SampleCollectionDashBoardColumns o) {
		// TODO Auto-generated method stub
		return this.count-o.count;
	}
	@Override
	public String toString() {
		return "SampleCollectionDashBoardColumns [count=" + count + ", columnValue=" + columnValue
				+ ", noOfVacutainers=" + noOfVacutainers + "]";
	}
	

	
}
