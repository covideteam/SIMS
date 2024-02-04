package com.covide.dto;

import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

public class ProjectParametersDto {
	public ProjectParametersDto() {
		
	}
	
	public ProjectParametersDto(LanguageSpecificGlobalParameterDetails globalParameter) {
		this.setId(globalParameter.getParameterId().getId());
		this.setDescription(globalParameter.getName());
	}
	
	private Long id;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
