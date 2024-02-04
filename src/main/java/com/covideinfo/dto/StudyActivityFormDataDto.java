package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class StudyActivityFormDataDto implements Serializable {
	
	private List<StudyActivityParameters> sapList;
	private List<GlobalParameter> gpList;
	private StudyVolunteerReporting volId;
	private StudyActivities stdActivity ;
	private StudyMaster sm;
	private StudyPeriodMaster spm;
	private StudySubjects subject;
	private List<ParameterControlTypeValues> pctvList;
	private StudyGroupPeriodMaster sgpm;
	private TreatmentInfo treatment;
	private Long elegibleForNextProcessParameterId;
	public List<StudyActivityParameters> getSapList() {
		return sapList;
	}
	public List<GlobalParameter> getGpList() {
		return gpList;
	}
	public StudyVolunteerReporting getVolId() {
		return volId;
	}
	public StudyActivities getStdActivity() {
		return stdActivity;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public void setSapList(List<StudyActivityParameters> sapList) {
		this.sapList = sapList;
	}
	public void setGpList(List<GlobalParameter> gpList) {
		this.gpList = gpList;
	}
	public void setVolId(StudyVolunteerReporting volId) {
		this.volId = volId;
	}
	public void setStdActivity(StudyActivities stdActivity) {
		this.stdActivity = stdActivity;
	}
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public List<ParameterControlTypeValues> getPctvList() {
		return pctvList;
	}
	public void setPctvList(List<ParameterControlTypeValues> pctvList) {
		this.pctvList = pctvList;
	}
	public StudyPeriodMaster getSpm() {
		return spm;
	}
	public void setSpm(StudyPeriodMaster spm) {
		this.spm = spm;
	}
	public StudyGroupPeriodMaster getSgpm() {
		return sgpm;
	}
	public void setSgpm(StudyGroupPeriodMaster sgpm) {
		this.sgpm = sgpm;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	public Long getElegibleForNextProcessParameterId() {
		return elegibleForNextProcessParameterId;
	}
	public void setElegibleForNextProcessParameterId(Long elegibleForNextProcessParameterId) {
		this.elegibleForNextProcessParameterId = elegibleForNextProcessParameterId;
	}
	
	
	
	

}
