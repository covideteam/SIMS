package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

public class ActivityEntryDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1966300280821348926L;
	
	private String performedBy;
	private Date performedOn;
	
	public String getPerformedBy() {
		return performedBy;
	}
	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}
	public Date getPerformedOn() {
		return performedOn;
	}
	public void setPerformedOn(Date performedOn) {
		this.performedOn = performedOn;
	}
	
	

}
