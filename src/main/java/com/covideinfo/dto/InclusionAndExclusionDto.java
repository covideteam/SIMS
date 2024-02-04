package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;

@SuppressWarnings("serial")
public class InclusionAndExclusionDto implements Serializable {
	
	private LanguageSpecificGlobalActivityDetails ga;
	private Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpMap;
	private Map<Long, LanguageSpecificGroupDetails> groupsMap;
	public LanguageSpecificGlobalActivityDetails getGa() {
		return ga;
	}
	public Map<Long, List<LanguageSpecificGlobalParameterDetails>> getGpMap() {
		return gpMap;
	}
	public Map<Long, LanguageSpecificGroupDetails> getGroupsMap() {
		return groupsMap;
	}
	public void setGa(LanguageSpecificGlobalActivityDetails ga) {
		this.ga = ga;
	}
	public void setGpMap(Map<Long, List<LanguageSpecificGlobalParameterDetails>> gpMap) {
		this.gpMap = gpMap;
	}
	public void setGroupsMap(Map<Long, LanguageSpecificGroupDetails> groupsMap) {
		this.groupsMap = groupsMap;
	}
	
	
	
	
	
	

}
