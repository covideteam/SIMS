package com.covide.dto;

import java.util.List;
import java.util.Map;

import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

public class UserStudyActivityDto {
	private List<UserWiseStudiesAsignMaster> userStudies;
	private Map<Long,List<StudyActivities>> studyActivities;
	private Map<Long,LanguageSpecificGlobalActivityDetails> gobalActivityList;
	public Map<Long, List<StudyActivities>> getStudyActivities() {
		return studyActivities;
	}
	public void setStudyActivities(Map<Long, List<StudyActivities>> studyList) {
		this.studyActivities = studyList;
	}
	public Map<Long,LanguageSpecificGlobalActivityDetails> getGobalActivities() {
		return gobalActivityList;
	}
	public void setGobalActivities(Map<Long,LanguageSpecificGlobalActivityDetails> gobalActivityList) {
		this.gobalActivityList = gobalActivityList;
	}
	public List<UserWiseStudiesAsignMaster> getUserStudies() {
		return userStudies;
	}
	public void setUserStudies(List<UserWiseStudiesAsignMaster> userStudies) {
		this.userStudies = userStudies;
	}
}
