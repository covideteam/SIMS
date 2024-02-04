package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class RulesDetails implements Serializable {
	
	private List<ValidationRulesDto> valRulesList;
	private List<ConditionRulesDto> crdtoList;
	private List<DependentRulesDto> dependentRulesList;
	private List<DependentActivitiesDto> dependentActList;
	
	public List<ValidationRulesDto> getValRulesList() {
		return valRulesList;
	}
	public void setValRulesList(List<ValidationRulesDto> valRulesList) {
		this.valRulesList = valRulesList;
	}
	public List<ConditionRulesDto> getCrdtoList() {
		return crdtoList;
	}
	public void setCrdtoList(List<ConditionRulesDto> crdtoList) {
		this.crdtoList = crdtoList;
	}
	public List<DependentRulesDto> getDependentRulesList() {
		return dependentRulesList;
	}
	public void setDependentRulesList(List<DependentRulesDto> dependentRulesList) {
		this.dependentRulesList = dependentRulesList;
	}
	public List<DependentActivitiesDto> getDependentActList() {
		return dependentActList;
	}
	public void setDependentActList(List<DependentActivitiesDto> dependentActList) {
		this.dependentActList = dependentActList;
	}
	
	
	

}
