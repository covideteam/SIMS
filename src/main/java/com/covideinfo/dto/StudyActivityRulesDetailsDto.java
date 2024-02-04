package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StudyActivityRuleMessages;
import com.covideinfo.model.StudyActivityRules;

@SuppressWarnings("serial")
public class StudyActivityRulesDetailsDto implements Serializable {
	
	private List<StudyActivityRules> stdactRList;
	private List<StudyActivityRuleMessages> stdactRMList;
	
	public List<StudyActivityRules> getStdactRList() {
		return stdactRList;
	}
	public void setStdactRList(List<StudyActivityRules> stdactRList) {
		this.stdactRList = stdactRList;
	}
	public List<StudyActivityRuleMessages> getStdactRMList() {
		return stdactRMList;
	}
	public void setStdactRMList(List<StudyActivityRuleMessages> stdactRMList) {
		this.stdactRMList = stdactRMList;
	}
	
	

}
