package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;

@SuppressWarnings("serial")
public class GroupOrderingPrametersDto implements Serializable {
	
	List<LanguageSpecificGlobalParameterDetails> groupOrderParmMap;
	List<LanguageSpecificGlobalParameterDetails> nonGroupParmMap;
	public List<LanguageSpecificGlobalParameterDetails> getGroupOrderParmMap() {
		return groupOrderParmMap;
	}
	public List<LanguageSpecificGlobalParameterDetails> getNonGroupParmMap() {
		return nonGroupParmMap;
	}
	public void setGroupOrderParmMap(List<LanguageSpecificGlobalParameterDetails> groupOrderParmMap) {
		this.groupOrderParmMap = groupOrderParmMap;
	}
	public void setNonGroupParmMap(List<LanguageSpecificGlobalParameterDetails> nonGroupParmMap) {
		this.nonGroupParmMap = nonGroupParmMap;
	}
	
	

}
