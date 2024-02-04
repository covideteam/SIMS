package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValuesDto implements Serializable {
	
	private Long parameterId;
	private Long lanspecificvalueId;
	private String valueName;
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	
	public Long getLanspecificvalueId() {
		return lanspecificvalueId;
	}
	public void setLanspecificvalueId(Long lanspecificvalueId) {
		this.lanspecificvalueId = lanspecificvalueId;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	

}
