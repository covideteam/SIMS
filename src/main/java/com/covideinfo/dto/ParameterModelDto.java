package com.covideinfo.dto;

import java.io.Serializable;

public class ParameterModelDto implements Serializable {
	
	private static final long serialVersionUID = 8265721134078284995L;
	private Long parameterId;
	private String parameterValue;
	private boolean isParameterFile;
	public Long getParameterId() {
		return parameterId;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public boolean isParameterFile() {
		return isParameterFile;
	}
	public void setParameterFile(boolean isParameterFile) {
		this.isParameterFile = isParameterFile;
	}
	
	
	

}
