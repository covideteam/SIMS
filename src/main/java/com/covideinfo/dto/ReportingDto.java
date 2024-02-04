package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class ReportingDto implements Serializable {
	private Long studyId;
	private Long groupId;
	private List<LanguageSpecificValueDetails> lsvList;

	public List<LanguageSpecificValueDetails> getLsvList() {
		return lsvList;
	}

	public void setLsvList(List<LanguageSpecificValueDetails> lsvList) {
		this.lsvList = lsvList;
	}

	public Long getStudyId() {
		return studyId;
	}

	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
}
