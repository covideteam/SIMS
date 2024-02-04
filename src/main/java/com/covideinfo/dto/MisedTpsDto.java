package com.covideinfo.dto;

import java.io.Serializable;

public class MisedTpsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4491534246580846443L;
	private String timePoint;
	private String tpDate;
	private String time;
	private int vacutainerNo;
	private String position;
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getTpDate() {
		return tpDate;
	}
	public void setTpDate(String tpDate) {
		this.tpDate = tpDate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getVacutainerNo() {
		return vacutainerNo;
	}
	public void setVacutainerNo(int vacutainerNo) {
		this.vacutainerNo = vacutainerNo;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	

}
