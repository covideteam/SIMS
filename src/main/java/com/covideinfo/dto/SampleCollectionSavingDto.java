package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class SampleCollectionSavingDto implements Serializable {
	
	private StudySubjects subject;
	private StudyPeriodMaster spm;
	private TreatmentInfo treatment;
	private SampleTimePoints sampletp;
	private StudyMaster study;
	private UserMaster user;
	public StudySubjects getSubject() {
		return subject;
	}
	public StudyPeriodMaster getSpm() {
		return spm;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public SampleTimePoints getSampletp() {
		return sampletp;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public void setSpm(StudyPeriodMaster spm) {
		this.spm = spm;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	public void setSampletp(SampleTimePoints sampletp) {
		this.sampletp = sampletp;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	
	
	

}
