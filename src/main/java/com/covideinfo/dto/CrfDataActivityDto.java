package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

@SuppressWarnings("serial")
public class CrfDataActivityDto implements Serializable {
	
	private List<LanguageSpecificGlobalParameterDetails> parmetersList;
	private Map<Long, String> paramValsMap;
	
	public List<LanguageSpecificGlobalParameterDetails> getParmetersList() {
		return parmetersList;
	}
	public void setParmetersList(List<LanguageSpecificGlobalParameterDetails> parmetersList) {
		this.parmetersList = parmetersList;
	}
	public Map<Long, String> getParamValsMap() {
		return paramValsMap;
	}
	public void setParamValsMap(Map<Long, String> paramValsMap) {
		this.paramValsMap = paramValsMap;
	}
	
	

}
