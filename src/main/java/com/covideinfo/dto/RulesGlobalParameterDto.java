package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RulesGlobalParameterDto implements Serializable {
	
	private Long parameterId;
	private Long lspParameterId;
	private String parameterName;
	private RulesControlDto controlType;
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public Long getLspParameterId() {
		return lspParameterId;
	}
	public void setLspParameterId(Long lspParameterId) {
		this.lspParameterId = lspParameterId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public RulesControlDto getControlType() {
		return controlType;
	}
	public void setControlType(RulesControlDto controlType) {
		this.controlType = controlType;
	}
	
	

}
