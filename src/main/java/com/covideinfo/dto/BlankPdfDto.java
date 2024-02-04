package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StaticActivityDetails;
import com.covideinfo.model.StaticActivityValueDetails;
import com.covideinfo.model.StudyPhases;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class BlankPdfDto implements Serializable {
	
	private Projects project;
	private String noOfPeriods;
	private UserMaster user;
	private ApplicationConfiguration appConfig;
	private ActivityDraftReviewAudit adrAudit;
	private Map<Long, StudyPhases> phasesMap;
	private List<StaticActivityDetails> staticActList;
	private List<StaticActivityValueDetails> staticActValList;
	private List<ProjectsDetails> sampleProList;
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public ApplicationConfiguration getAppConfig() {
		return appConfig;
	}
	public void setAppConfig(ApplicationConfiguration appConfig) {
		this.appConfig = appConfig;
	}
	public Projects getProject() {
		return project;
	}
	public String getNoOfPeriods() {
		return noOfPeriods;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public void setNoOfPeriods(String noOfPeriods) {
		this.noOfPeriods = noOfPeriods;
	}
	public ActivityDraftReviewAudit getAdrAudit() {
		return adrAudit;
	}
	public void setAdrAudit(ActivityDraftReviewAudit adrAudit) {
		this.adrAudit = adrAudit;
	}
	public Map<Long, StudyPhases> getPhasesMap() {
		return phasesMap;
	}
	public void setPhasesMap(Map<Long, StudyPhases> phasesMap) {
		this.phasesMap = phasesMap;
	}
	public List<StaticActivityDetails> getStaticActList() {
		return staticActList;
	}
	public void setStaticActList(List<StaticActivityDetails> staticActList) {
		this.staticActList = staticActList;
	}
	public List<StaticActivityValueDetails> getStaticActValList() {
		return staticActValList;
	}
	public void setStaticActValList(List<StaticActivityValueDetails> staticActValList) {
		this.staticActValList = staticActValList;
	}
	public List<ProjectsDetails> getSampleProList() {
		return sampleProList;
	}
	public void setSampleProList(List<ProjectsDetails> sampleProList) {
		this.sampleProList = sampleProList;
	}
	
	

}
