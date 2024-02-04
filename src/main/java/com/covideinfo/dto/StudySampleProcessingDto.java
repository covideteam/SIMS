package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleProcessingDto implements Serializable {
	
	private static final long serialVersionUID = 8120069373568621478L;
	private int allowedTime;
	private String allowedTimeFrom;
	private String conditions;
	private boolean matrixDifferent;
	public int getAllowedTime() {
		return allowedTime;
	}
	public String getAllowedTimeFrom() {
		return allowedTimeFrom;
	}
	public String getConditions() {
		return conditions;
	}
	public boolean isMatrixDifferent() {
		return matrixDifferent;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public void setAllowedTimeFrom(String allowedTimeFrom) {
		this.allowedTimeFrom = allowedTimeFrom;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public void setMatrixDifferent(boolean matrixDifferent) {
		this.matrixDifferent = matrixDifferent;
	}
	
	
	

}
