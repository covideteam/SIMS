package com.covide.dto;

import java.util.List;
import java.util.Map;

import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyActivities;

public class StudyActivityDto {
	private Map<Long,List<StudyActivities>> studyList;
	private List<LanguageSpecificGlobalActivityDetails> gobalActivityList;
	public Map<Long, List<StudyActivities>> getStudyList() {
		return studyList;
	}
	public void setStudyList(Map<Long, List<StudyActivities>> studyList) {
		this.studyList = studyList;
	}
	public List<LanguageSpecificGlobalActivityDetails> getGobalActivityList() {
		return gobalActivityList;
	}
	public void setGobalActivityList(List<LanguageSpecificGlobalActivityDetails> gobalActivityList) {
		this.gobalActivityList = gobalActivityList;
	}
	
	
}
