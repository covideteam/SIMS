package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class GlobalparameterFromDto implements Serializable {

	private List<GlobalParameterDetailsDto> gpdDtoList;
	private GlobalActivityParameterDetailsDto gapDto;
	private RulesDetails rules;
	public List<GlobalParameterDetailsDto> getGpdDtoList() {
		return gpdDtoList;
	}
	public GlobalActivityParameterDetailsDto getGapDto() {
		return gapDto;
	}
	public RulesDetails getRules() {
		return rules;
	}
	public void setGpdDtoList(List<GlobalParameterDetailsDto> gpdDtoList) {
		this.gpdDtoList = gpdDtoList;
	}
	public void setGapDto(GlobalActivityParameterDetailsDto gapDto) {
		this.gapDto = gapDto;
	}
	public void setRules(RulesDetails rules) {
		this.rules = rules;
	}
	
	
}
