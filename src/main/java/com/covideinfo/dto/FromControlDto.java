package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class FromControlDto implements Serializable {
	
	private String contentCode;
	private List<FromValuesDto> valuesDto;
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public List<FromValuesDto> getValuesDto() {
		return valuesDto;
	}
	public void setValuesDto(List<FromValuesDto> valuesDto) {
		this.valuesDto = valuesDto;
	}
	
	
	
	
	

}
