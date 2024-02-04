package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StaticActivityParamtersValuesDto implements Serializable {
	
	private Long globalValId;
	private String paramterValue;
	public Long getGlobalValId() {
		return globalValId;
	}
	public void setGlobalValId(Long globalValId) {
		this.globalValId = globalValId;
	}
	public String getParamterValue() {
		return paramterValue;
	}
	public void setParamterValue(String paramterValue) {
		this.paramterValue = paramterValue;
	}

}
