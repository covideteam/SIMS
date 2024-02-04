package com.covideinfo.dto;

import java.io.Serializable;

import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;

public class DoseSavingDtoDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1036792408521849797L;
	private StudyPeriodMaster period;
	private UserMaster user;
	private DoseTimePoints dosingTimePoint;
	private StudySubjects subject;
	private StudySubjects replaceSubject;
	private DeviationMessage dvm;
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public UserMaster getUser() {
		return user;
	}
	public DoseTimePoints getDosingTimePoint() {
		return dosingTimePoint;
	}
	public StudySubjects getSubject() {
		return subject;
	}
	public StudySubjects getReplaceSubject() {
		return replaceSubject;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public void setDosingTimePoint(DoseTimePoints dosingTimePoint) {
		this.dosingTimePoint = dosingTimePoint;
	}
	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}
	public void setReplaceSubject(StudySubjects replaceSubject) {
		this.replaceSubject = replaceSubject;
	}
	public DeviationMessage getDvm() {
		return dvm;
	}
	public void setDvm(DeviationMessage dvm) {
		this.dvm = dvm;
	}
	
	
	

}
