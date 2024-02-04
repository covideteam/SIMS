package com.covideinfo.bo;

import java.util.List;

public class SampleCollectionDashBoard {
	String heading  = "";
	String treatment = "";
	List<SampleCollectionDashBoardColumns> columns;
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public List<SampleCollectionDashBoardColumns> getColumns() {
		return columns;
	}
	public void setColumns(List<SampleCollectionDashBoardColumns> columns) {
		this.columns = columns;
	}
	public SampleCollectionDashBoard(String heading, List<SampleCollectionDashBoardColumns> columns) {
		super();
		this.heading = heading;
		this.columns = columns;
	}
	public SampleCollectionDashBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SampleCollectionDashBoard(String heading, String treatment, List<SampleCollectionDashBoardColumns> columns) {
		super();
		this.heading = heading;
		this.treatment = treatment;
		this.columns = columns;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	
	
}
