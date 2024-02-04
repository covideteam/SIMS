package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.UserMaster;

public class DosingCollectionTimePointsDto implements Serializable {
	
	private SubjectDoseTimePoints sdtp;
	private  StudyPeriodMaster period; 
	private UserMaster user;
	private StudySubjects subject;
	private DoseTimePoints dosingTimePoint;
	private DoseTimePoints firstDose;
	private StudySubjects replaceSubject;
	private DeviationMessage devMsg;

	

	public SubjectDoseTimePoints getSdtp() {
		return sdtp;
	}

	public void setSdtp(SubjectDoseTimePoints sdtp) {
		this.sdtp = sdtp;
	}

	public StudyPeriodMaster getPeriod() {
		return period;
	}

	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}

	public StudySubjects getSubject() {
		return subject;
	}

	public void setSubject(StudySubjects subject) {
		this.subject = subject;
	}

	public DoseTimePoints getDosingTimePoint() {
		return dosingTimePoint;
	}

	public void setDosingTimePoint(DoseTimePoints dosingTimePoint) {
		this.dosingTimePoint = dosingTimePoint;
	}

	public DoseTimePoints getFirstDose() {
		return firstDose;
	}

	public void setFirstDose(DoseTimePoints firstDose) {
		this.firstDose = firstDose;
	}

	public StudySubjects getReplaceSubject() {
		return replaceSubject;
	}

	public DeviationMessage getDevMsg() {
		return devMsg;
	}

	public void setReplaceSubject(StudySubjects replaceSubject) {
		this.replaceSubject = replaceSubject;
	}

	public void setDevMsg(DeviationMessage devMsg) {
		this.devMsg = devMsg;
	}
	
	

}
