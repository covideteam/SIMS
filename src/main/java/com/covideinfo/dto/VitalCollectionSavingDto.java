package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;

@SuppressWarnings("serial")
public class VitalCollectionSavingDto implements Serializable{
	
	private StudyMaster study;
	private UserMaster user;
	private StudySubjects subject;
	private VitalTimePoints vac;
	private StudyPeriodMaster period;
	public UserMaster getUser() {
		return user;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public VitalTimePoints getVac() {
		return vac;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public void setVac(VitalTimePoints vac) {
		this.vac = vac;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}

}
