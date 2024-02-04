package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RulesControlDto implements Serializable {
	
	private String controlType;
	private Long controlTypeId;
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public Long getControlTypeId() {
		return controlTypeId;
	}
	public void setControlTypeId(Long controlTypeId) {
		this.controlTypeId = controlTypeId;
	}
	
	

}
